import 'package:flutter/material.dart';
import 'package:mobile/components/app_navibar.dart';
import 'package:mobile/providers/routine_provider.dart';
import 'package:mobile/providers/task_provider.dart';
import 'package:mobile/screens/library/components/routine_card.dart';
import 'package:mobile/screens/library/components/task_card.dart';
import 'package:provider/provider.dart';

class LibraryScreen extends StatefulWidget {
  const LibraryScreen({super.key});

  @override
  State<LibraryScreen> createState() => _LibraryScreenState();
}

class _LibraryScreenState extends State<LibraryScreen> {
  bool isTask = false;
  ScrollController scrollController = ScrollController();

  TextEditingController searchController = TextEditingController();
  TextEditingController titleController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();

  @override
  void dispose() {
    super.dispose();
    searchController.dispose();
    titleController.dispose();
    descriptionController.dispose();
    scrollController.dispose();
  }

  void _openTaskForm(BuildContext context) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      builder: (context) {
        return FractionallySizedBox(
          heightFactor: 0.9,
          child: Padding(
            padding: EdgeInsets.only(
              bottom: MediaQuery.of(context).viewInsets.bottom,
              left: 16,
              right: 16,
              top: 16,
            ),
            child: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text("Cadastro de ${isTask ? 'Tarefa' : 'Rotina'}"),
                  const SizedBox(height: 16),
                  Form(
                    child: Column(
                      children: [
                        TextFormField(
                          controller: titleController,
                          decoration: const InputDecoration(
                            label: Text("Título"),
                          ),
                        ),
                        TextFormField(
                          controller: descriptionController,
                          decoration: const InputDecoration(
                            label: Text("Descrição"),
                          ),
                        ),
                        const SizedBox(height: 16),
                        Row(
                          children: [
                            Expanded(
                              child: ElevatedButton(
                                onPressed: () async {
                                  if (isTask) {
                                    await context.read<TaskProvider>().create(
                                      title: titleController.text,
                                      description: descriptionController.text,
                                    );
                                  } else {
                                    RoutineProvider routine = context
                                        .read<RoutineProvider>();

                                    await routine.create(
                                      title: titleController.text,
                                      description: descriptionController.text,
                                    );

                                    if (routine.error != null) {
                                      ScaffoldMessenger.of(
                                        context,
                                      ).showSnackBar(
                                        SnackBar(content: Text(routine.error!)),
                                      );
                                    }

                                    if (context.mounted) Navigator.pop(context);
                                  }
                                },
                                child: const Text("Adicionar"),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }

  void _onScroll() {
    // chegou perto do fim (200px de margem)
    if (scrollController.position.pixels >=
        scrollController.position.maxScrollExtent - 200) {
      if (isTask) {
        // context.read<TaskProvider>().loadMore();
      } else {
        context.read<RoutineProvider>().loadMore();
      }
    }
  }

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      RoutineProvider routine = context.read<RoutineProvider>();
      routine.findAll(refresh: true);

      if (routine.error != null) {
        ScaffoldMessenger.of(
          context,
        ).showSnackBar(SnackBar(content: Text(routine.error!)));
      }
    });

    scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    var routineProvider = Provider.of<RoutineProvider>(context);
    var taskProvider = Provider.of<TaskProvider>(context);

    return Scaffold(
      bottomNavigationBar: AppNavibar(
        currentRoute: ModalRoute.of(context)!.settings.name!,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          spacing: 16,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Column(
                  children: [
                    Text("Sua biblioteca"),
                    Text("Tarefas & Rotinas", style: TextStyle(fontSize: 24)),
                  ],
                ),
                IconButton(
                  onPressed: () => _openTaskForm(context),
                  icon: Icon(Icons.add),
                ),
              ],
            ),
            Column(
              spacing: 16,
              children: [
                SizedBox(
                  width: double.infinity,
                  child: Container(
                    height: 60,
                    padding: EdgeInsetsGeometry.all(4),
                    decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      spacing: 4,
                      children: [
                        Expanded(
                          child: FilledButton(
                            style: FilledButton.styleFrom(
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(6),
                              ),
                              backgroundColor: isTask
                                  ? Colors.transparent
                                  : Colors.white,
                            ),
                            onPressed: () {
                              setState(() {
                                isTask = false;
                              });
                              routineProvider.findAll(refresh: true);
                            },
                            child: Row(
                              spacing: 16,
                              children: [
                                Icon(
                                  Icons.calendar_month_outlined,
                                  color: isTask
                                      ? Colors.black38
                                      : Colors.blueAccent,
                                ),
                                Text(
                                  "Rotinas",
                                  style: TextStyle(
                                    color: isTask
                                        ? Colors.black38
                                        : Colors.blueAccent,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        Expanded(
                          child: FilledButton(
                            style: FilledButton.styleFrom(
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(6),
                              ),
                              backgroundColor: isTask
                                  ? Colors.white
                                  : Colors.transparent,
                            ),
                            onPressed: () {
                              setState(() {
                                isTask = true;
                              });
                              taskProvider.findAll();
                            },
                            child: Row(
                              spacing: 16,
                              children: [
                                Icon(
                                  Icons.calendar_month_outlined,
                                  color: isTask
                                      ? Colors.blueAccent
                                      : Colors.black38,
                                ),
                                Text(
                                  "Tarefas",
                                  style: TextStyle(
                                    color: isTask
                                        ? Colors.blueAccent
                                        : Colors.black38,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
                TextField(
                  controller: searchController,
                  decoration: InputDecoration(
                    prefixIcon: Icon(Icons.search),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.all(Radius.circular(16)),
                    ),
                    label: Text("Buscar por ${isTask ? 'Tarefa' : 'Rotina'}"),
                  ),
                ),
              ],
            ),
            Expanded(
              child: isTask
                  ? ListView.builder(
                      controller: scrollController,
                      itemCount: taskProvider.tasks.length,
                      itemBuilder: (context, index) {
                        final t = taskProvider.tasks[index];
                        return TaskCard(task: t);
                      },
                    )
                  : ListView.builder(
                      controller: scrollController,
                      itemCount: routineProvider.routines.length,
                      itemBuilder: (context, index) {
                        final r = routineProvider.routines[index];
                        return RoutineCard(routine: r);
                      },
                    ),
            ),
          ],
        ),
      ),
    );
  }
}

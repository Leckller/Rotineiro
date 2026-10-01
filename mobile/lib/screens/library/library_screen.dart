import 'package:flutter/material.dart';
import 'package:mobile/components/app_navibar.dart';
import 'package:mobile/providers/routine_provider.dart';
import 'package:mobile/providers/task_provider.dart';
import 'package:provider/provider.dart';

class LibraryScreen extends StatefulWidget {
  const LibraryScreen({super.key});

  @override
  State<LibraryScreen> createState() => _LibraryScreenState();
}

class _LibraryScreenState extends State<LibraryScreen> {
  bool isTask = false;
  TextEditingController searchController = TextEditingController();


  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      context.read<RoutineProvider>().findAll();
    });
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
                  onPressed: () => {},
                  icon: Icon(Icons.add),
                ),
              ],
            ),
            Column(
              children: [
                SizedBox(
                  width: double.infinity,
                  child: SegmentedButton<bool>(
                    segments: [
                      ButtonSegment(
                        value: false,
                        icon: Icon(Icons.calendar_month_outlined),
                        label: Text("Rotinas"),
                      ),
                      ButtonSegment(
                        value: true,
                        icon: Icon(Icons.task),
                        label: Text("Tarefas"),
                      ),
                    ],
                    selected: {isTask},
                    onSelectionChanged: (_) {
                      setState(() {
                        isTask = !isTask;
                        if (isTask) {
                          taskProvider.findAll();
                        } else {
                          routineProvider.findAll();
                        }
                      });
                    },
                  ),
                ),
                TextField(
                  controller: searchController,
                  decoration: InputDecoration(
                    label: Text("Buscar por ${isTask ? 'Tarefa' : 'Rotina'}"),
                  ),
                ),
              ],
            ),
            Expanded(
              child: isTask
                  ? ListView.builder(
                      itemCount: taskProvider.tasks.length,
                      itemBuilder: (context, index) {
                        final t = taskProvider.tasks[index];
                        return SizedBox(
                          height: 80,
                          width: double.infinity,
                          child: Card(child: Text(t.title)),
                        );
                      },
                    )
                  : ListView.builder(
                      itemCount: routineProvider.routines.length,
                      itemBuilder: (context, index) {
                        final r = routineProvider.routines[index];
                        return SizedBox(
                          height: 80,
                          width: double.infinity,
                          child: Card(child: Text(r.title)),
                        );
                      },
                    ),
            ),
          ],
        ),
      ),
    );
  }
}

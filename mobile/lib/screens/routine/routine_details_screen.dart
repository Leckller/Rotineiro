import 'package:flutter/material.dart';
import 'package:mobile/models/routine.dart';
import 'package:mobile/providers/routine_provider.dart';
import 'package:provider/provider.dart';

class RoutineDetailsScreen extends StatefulWidget {
  const RoutineDetailsScreen({super.key});

  @override
  State<RoutineDetailsScreen> createState() => _RoutineDetailsScreenState();
}

class _RoutineDetailsScreenState extends State<RoutineDetailsScreen> {
  TextEditingController titleController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();
  late Routine routine;
  bool initialized = false;

  @override
  void dispose() {
    super.dispose();
    titleController.dispose();
    descriptionController.dispose();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();

    if (initialized) return;

    routine = ModalRoute.of(context)!.settings.arguments as Routine;

    titleController.text = routine.title;
    descriptionController.text = routine.description;

    initialized = true;
  }

  Future<void> updateRoutine(int id) async {
    final p = context.read<RoutineProvider>();
    
    await p.update(
      id: id,
      title: titleController.text,
      description: descriptionController.text,
    );

    if (!mounted) return;
    
    if (p.error != null) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text(p.error!)));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          icon: Icon(Icons.arrow_back),
        ),
        title: Text(
          "Editar Rotina",
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
      ),
      body: Container(
        padding: EdgeInsetsGeometry.all(8),
        color: const Color.fromARGB(8, 0, 0, 0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Column(
              children: [
                SizedBox(
                  height: 100,
                  child: Card(
                    color: Colors.white,
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Column(
                        children: [
                          Row(children: [Text("Nome da tarefa")]),
                          TextField(
                            controller: titleController,
                            decoration: InputDecoration(label: Text("teste")),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
                SizedBox(
                  height: 100,
                  child: Card(
                    color: Colors.white,
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Column(
                        children: [
                          Row(
                            spacing: 4,
                            children: [
                              Text("Descrição"),
                              Text(
                                "(Opcional)",
                                style: TextStyle(color: Colors.grey),
                              ),
                            ],
                          ),
                          TextField(
                            controller: descriptionController,
                            decoration: InputDecoration(
                              label: Text("Descrição"),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
                Card(
                  color: Colors.white,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Column(
                      spacing: 8,
                      children: [
                        Row(
                          spacing: 4,
                          children: [
                            Text("Adicionar as tarefas"),
                            Text(
                              "(Opcional)",
                              style: TextStyle(color: Colors.grey),
                            ),
                          ],
                        ),
                        Text("Nenhuma tarefa vinculada!"),
                        // SizedBox(
                        //   height: 75,
                        //   child: Card(
                        //     child: Column(
                        //       mainAxisAlignment: MainAxisAlignment.center,
                        //       crossAxisAlignment: CrossAxisAlignment.start,
                        //       children: [
                        //         Text(
                        //           "tarefa",
                        //           overflow: TextOverflow.ellipsis,
                        //           style: TextStyle(
                        //             fontWeight: FontWeight.w600,
                        //             fontSize: 18,
                        //           ),
                        //         ),
                        //         Text(
                        //           "descrição",
                        //           style: TextStyle(
                        //             fontSize: 14,
                        //             color: Colors.grey,
                        //           ),
                        //         ),
                        //       ],
                        //     ),
                        //   ),
                        // ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
            Row(
              children: [
                Expanded(
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.white,
                    ),
                    onPressed: () {
                      updateRoutine(routine.id);
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Text("Salvar", style: TextStyle(fontSize: 16)),
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

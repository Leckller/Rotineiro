import 'package:flutter/material.dart';
import 'package:mobile/models/routine.dart';

class RoutineDetailsScreen extends StatefulWidget {
  const RoutineDetailsScreen({super.key});

  @override
  State<RoutineDetailsScreen> createState() => _RoutineDetailsScreenState();
}

class _RoutineDetailsScreenState extends State<RoutineDetailsScreen> {
  TextEditingController titleController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final Routine routine = ModalRoute.of(context)?.settings.arguments as Routine;

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
                        controller: titleController,
                        decoration: InputDecoration(label: Text("teste")),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            SizedBox(
              height: 300,
              child: Card(
                color: Colors.white,
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Column(
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
                      SizedBox(
                        height: 75,
                        child: Card(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                "tarefa",
                                overflow: TextOverflow.ellipsis,
                                style: TextStyle(
                                  fontWeight: FontWeight.w600,
                                  fontSize: 18,
                                ),
                              ),
                              Text(
                                "descrição",
                                style: TextStyle(
                                  fontSize: 14,
                                  color: Colors.grey,
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
            ),
          ],
        ),
      ),
    );
  }
}

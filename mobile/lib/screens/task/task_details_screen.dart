import 'package:flutter/material.dart';
import 'package:mobile/models/task.dart';
import 'package:mobile/providers/task_provider.dart';
import 'package:provider/provider.dart';

class TaskDetailsScreen extends StatefulWidget {
  const TaskDetailsScreen({super.key});

  @override
  State<TaskDetailsScreen> createState() => _TaskDetailsScreenState();
}

class _TaskDetailsScreenState extends State<TaskDetailsScreen> {
  TextEditingController titleController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();
  bool initialized = false;

  late Task task;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();

    if (initialized) return;

    task = ModalRoute.of(context)?.settings.arguments as Task;

    titleController.text = task.title;
    descriptionController.text = task.description;

    initialized = true;
  }

  Future<void> updateTask() async {
    final p = context.read<TaskProvider>();

    await p.update(
      id: task.id,
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

  Future<void> deleteTask() async {
    final p = context.read<TaskProvider>();

    await p.delete(task.id);

    if (!mounted) return;

    if (p.error != null) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text(p.error!)));
    } else {
      Navigator.pop(context);
    }
  }

  Future<void> _confirmDelete() async {
    final confirmed = await showDialog(
      context: context,
      builder: (ctx) {
        return AlertDialog(
          title: Text("Tem certeza que deseja apagar essa tarefa?"),
          content: Text("Esta ação é irreversível."),
          actions: [
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop(false);
              },
              style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              child: Text("Cancelar", style: TextStyle(color: Colors.white)),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop(true);
              },
              style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
              child: Text("Confirmar", style: TextStyle(color: Colors.black)),
            ),
          ],
        );
      },
    );

    if (confirmed == true) {
      await deleteTask();
    }
  }

  Widget _card({
    required String title,
    String subtitle = "",
    required TextEditingController controller,
    required String label,
  }) {
    return Card(
      color: Colors.white,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          spacing: 16,
          children: [
            Row(
              spacing: 8,
              children: [
                Text(title),
                if (subtitle.isNotEmpty)
                  Text(subtitle, style: TextStyle(color: Colors.grey)),
              ],
            ),
            TextField(
              controller: controller,
              decoration: InputDecoration(
                label: Text(label),
                border: OutlineInputBorder(),
              ),
            ),
          ],
        ),
      ),
    );
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
        actions: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: IconButton(
              onPressed: () {
                _confirmDelete();
              },
              icon: Icon(Icons.delete),
            ),
          ),
        ],
        title: Text("Editar Tarefa"),
      ),
      body: Container(
        width: double.infinity,
        padding: EdgeInsetsGeometry.all(8),
        color: Colors.black12,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Column(
              children: [
                _card(
                  title: "Titulo da tarefa",
                  controller: titleController,
                  label: "Título",
                ),
                _card(
                  title: "Descrição da tarefa",
                  subtitle: "(opcional)",
                  controller: descriptionController,
                  label: "Descrição",
                ),
                Card(
                  color: Colors.white,
                  child: Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      spacing: 16,
                      children: [
                        Row(
                          spacing: 8,
                          children: [
                            Text("Adicionar às rotinas"),
                            Text(
                              "(opcional)",
                              style: TextStyle(color: Colors.grey),
                            ),
                          ],
                        ),
                        Container(
                          decoration: BoxDecoration(
                            border: BoxBorder.all(color: Colors.grey),
                            borderRadius: BorderRadiusGeometry.circular(8),
                          ),
                          padding: EdgeInsetsGeometry.all(8),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text("Nome da rotina"),
                                  Text("x tarefas"),
                                ],
                              ),
                              Checkbox(value: false, onChanged: (_) {}),
                            ],
                          ),
                        ),
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
                      updateTask();
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

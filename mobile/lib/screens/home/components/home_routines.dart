import 'package:flutter/material.dart';

class HomeStartRoutine extends StatelessWidget {
  HomeStartRoutine({
    super.key,
    required this.routineName,
    required this.estimatedTime,
    required this.taskCount,
  });

  final String routineName;
  final String taskCount;
  final String estimatedTime;

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 8,
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Row(
          children: [
            ColoredBox(color: Colors.green, child: Icon(Icons.sunny)),
            Column(children: [Text(routineName), Text("${taskCount} tarefas - ${taskCount}")]),
          ],
        ),
      ),
    );
  }
}

class HomeRoutines extends StatelessWidget {
  const HomeRoutines({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            Text("Suas rotinas"),
            TextButton(onPressed: () {}, child: Text("Ver Todas")),
            HomeStartRoutine(routineName: routineName, estimatedTime: estimatedTime, taskCount: taskCount)
          ],
        ),
      ],
    );
  }
}

import 'package:flutter/material.dart';
import 'package:mobile/models/task.dart';
import 'package:mobile/service/task_service.dart';

class TaskProvider extends ChangeNotifier {
  TaskProvider({required this._service});

  final TaskService _service;

  List<Task> tasks = [];
  int total = 0;
  int page = 0;
  int pageSize = 15;
  bool loading = false;
  String? error;

  Future<void> findAll() async {
    loading = true;
    error = null;
    notifyListeners();

    try {
      tasks = await _service.findAll(page: page, pageSize: pageSize);
    } catch (e) {
      error = 'Não foi possível carregar as tarefas';
    }

    loading = false;
    notifyListeners();
  }

  Future<void> create({required String title, String description = ""}) async {
    loading = true;
    error = null;
    notifyListeners();

    try {
      int taskID = await _service.create(
        title: title,
        description: description,
      );
      Task newTask = Task(id: taskID, title: title, description: description);
      tasks.insert(0, newTask);
    } catch (e) {
      error = 'Não foi possível cadastrar a tarefa';
    }

    loading = false;
    notifyListeners();
  }

  Future<void> update({
    required int id,
    String title = "",
    String description = "",
  }) async {
    loading = true;
    error = null;
    notifyListeners();

    try {
      await _service.update(id: id, title: title, description: description);

      int index = tasks.indexWhere((t) => t.id == id);
      if (title.isNotEmpty) {
        tasks[index].title = title;
      }

      if (description.isNotEmpty) {
        tasks[index].description = description;
      }
    } catch (e) {
      error = 'Não foi possível atualizar a tarefa';
    }

    loading = false;
    notifyListeners();
  }

  Future<void> delete(int id) async {
    loading = true;
    error = null;
    notifyListeners();

    try {
      await _service.delete(id);
      tasks.removeWhere((r) => r.id == id);
    } catch (e) {
      error = 'Não foi possível deletar a tarefa';
    }

    loading = false;
    notifyListeners();
  }
}

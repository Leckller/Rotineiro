import 'package:flutter/material.dart';
import 'package:mobile/models/routine.dart';
import 'package:mobile/service/routine_service.dart';

class RoutineProvider extends ChangeNotifier {
  RoutineProvider({required this._service});

  final RoutineService _service;

  List<Routine> routines = [];
  bool loading = false;
  String? error;

  Future<void> findAll() async {
    loading = true;
    error = null;
    notifyListeners();

    try {
      routines = await _service.findAll();
    } catch (e) {
      error = 'Não foi possível carregar os produtos';
    }

    loading = false;
    notifyListeners();
  }

  Future<void> create({required String title, String description = ""}) async {
    loading = true;
    error = null;
    notifyListeners();

    try {
      int routineID = await _service.create(
        title: title,
        description: description,
      );

      Routine newRoutine = Routine(
        id: routineID,
        title: title,
        description: description,
      );
      routines.insert(0, newRoutine);
    } catch (e) {
      error = 'Não foi possível cadastrar a rotina';
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

      int index = routines.indexWhere((t) => t.id == id);
      if (title.isNotEmpty) {
        routines[index].title = title;
      }

      if (description.isNotEmpty) {
        routines[index].description = description;
      }
    } catch (e) {
      error = 'Não foi possível atualizar a rotina';
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
      routines.removeWhere((r) => r.id == id);
    } catch (e) {
      error = 'Não foi possível deletar a rotina';
    }

    loading = false;
    notifyListeners();

  }
}

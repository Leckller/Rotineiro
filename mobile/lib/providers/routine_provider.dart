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
}

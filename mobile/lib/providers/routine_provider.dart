import 'package:flutter/material.dart';
import 'package:mobile/models/routine.dart';
import 'package:mobile/service/routine_service.dart';

class RoutineProvider extends ChangeNotifier {
  final _service = RoutineService();

  List<Routine> routines = [];
  bool loading = false;
  String? error;

  Future<void> load() async {
    loading = true;
    error = null;
    notifyListeners();
    try {
      routines = await _service.fetchAll();
    } catch (e) {
      error = 'Não foi possível carregar os produtos';
    }
    loading = false;
    notifyListeners();
  }
}
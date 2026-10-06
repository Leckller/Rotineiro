import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/models/query_result.dart';
import 'package:mobile/models/routine.dart';
import 'package:mobile/service/routine_service.dart';

class RoutineProvider extends ChangeNotifier {
  RoutineProvider({required this._service});

  final RoutineService _service;

  List<Routine> routines = [];
  int total = 0;
  int totalPages = 0;
  int page = 1;
  int pageSize = 15;
  bool isLoadingMore = false;
  bool loading = false;
  String? error;

  Future<void> loadMore() async {
    if (isLoadingMore || routines.length >= total) return;
    isLoadingMore = true;
    notifyListeners();

    try {
      page++;
      final result = await _service.findAll(page: page, pageSize: pageSize);
      total = result.total;
      totalPages = result.totalPages;
      routines.addAll(result.data);
    } catch (e) {
      page--;
      error = 'Não foi possível carregar mais rotinas';
    }

    isLoadingMore = false;
    notifyListeners();
  }

  Future<void> findAll({bool refresh = false}) async {
    if (refresh) {
      page = 1;
      routines = [];
    }

    loading = true;
    error = null;
    notifyListeners();

    try {
      QueryResult result = await _service.findAll(
        page: page,
        pageSize: pageSize,
      );
  
      routines = result.data;
      page = result.page;
      pageSize = result.pageSize;
      total = result.total;
      totalPages = result.totalPages;
      
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
      if (index != -1) {
        if (title.isNotEmpty) routines[index].title = title;
        if (description.isNotEmpty) routines[index].description = description;
      }

    }
    // on DioException catch (e) {
    //   print(e.response?.data.toString());
    // } 
    catch (e) {
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

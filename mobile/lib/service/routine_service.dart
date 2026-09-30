import 'package:dio/dio.dart';

import '../models/routine.dart';

class RoutineService {
  RoutineService(this._dio);
  final Dio _dio;

  Future<List<Routine>> findAll() async {
    final res = await _dio.get('/routines');
    return (res.data as List).map((j) => Routine.fromJson(j)).toList();
  }

}

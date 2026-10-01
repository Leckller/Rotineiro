import 'package:dio/dio.dart';

import '../models/routine.dart';

class RoutineService {
  RoutineService(this._dio);
  final Dio _dio;

  Future<List<Routine>> findAll() async {
    final res = await _dio.get('/routines');
    // print(res.data.toString());
    return (res.data['data'] as List).map((j) => Routine.fromJson(j)).toList();
  }

  Future<int> create({required String title, String description = ""}) async {
    final res = await _dio.post(
      "/routines",
      data: {"title": title, "description": description},
    );
    return res.data['routineID'] ?? 0;
  }

  Future<void> update({
    required int id,
    String title = "",
    String description = "",
  }) async {
    Map<String, dynamic> data = {"id": id};

    if (title.isNotEmpty) {
      data['title'] = title;
    }

    if (description.isNotEmpty) {
      data['description'] = description;
    }

    await _dio.patch("/routines", data: data);
  }

  Future<void> delete(int id) async {
    await _dio.delete("routines/delete/$id");
  }
}

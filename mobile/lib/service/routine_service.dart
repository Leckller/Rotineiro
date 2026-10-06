import 'package:dio/dio.dart';
import 'package:mobile/models/query_result.dart';

import '../models/routine.dart';

class RoutineService {
  RoutineService(this._dio);
  final Dio _dio;

  Future<QueryResult<List<Routine>>> findAll({
    required int page,
    required int pageSize,
  }) async {
    final res = await _dio.get(
      '/routines',
      queryParameters: {"page": page, "pageSize": pageSize},
    );
    List<Routine> routines = (res.data['data'] as List)
        .map((j) => Routine.fromJson(j))
        .toList();
    return QueryResult(
      page: res.data['meta']['page'],
      pageSize: res.data['meta']['pageSize'],
      total: res.data['meta']['total'],
      totalPages: res.data['meta']['totalPages'],
      data: routines,
    );
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
    Map<String, dynamic> data = {};

    data['id'] = id;

    if (title.isNotEmpty) {
      data['title'] = title;
    }

    if (description.isNotEmpty) {
      data['description'] = description;
    }
    print(data.toString());

    await _dio.patch("/routines", data: data);
  }

  Future<void> delete(int id) async {
    await _dio.delete("routines/delete/$id");
  }
}

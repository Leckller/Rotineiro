import 'package:dio/dio.dart';
import 'package:mobile/models/task.dart';

class TaskService {
  TaskService(this._dio);
  final Dio _dio;

  Future<List<Task>> findAll({required int page, required int pageSize}) async {
    final res = await _dio.get('/tasks', queryParameters: {"page": page, "pageSize": pageSize});
    return (res.data['data'] as List).map((j) => Task.fromJson(j)).toList();
  }

  Future<int> create({required String title, String description = ""}) async {
    final res = await _dio.post(
      "/tasks",
      data: {"title": title, "description": description},
    );
    return res.data['taskID'] ?? 0;
  }

  Future<void> update({required int id, String title = "", String description = ""}) async {
    Map<String, dynamic> data = {
      "id": id,
    };

    if(title.isNotEmpty) {
      data['title'] = title;
    }
    
    if(description.isNotEmpty) {
      data['description'] = description;
    }

    await _dio.patch("/task", data: data); 
  }

  Future<void> delete(int id) async {
    await _dio.delete("task/delete/$id");
  }

}

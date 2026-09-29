import 'package:mobile/core/api_client.dart';

import '../models/routine.dart';

class RoutineService {
    Future<List<Routine>> fetchAll() async {
      final res = await dio.get('/routines');
      return (res.data as List).map((j) => Routine.fromJson(j)).toList();
    }
}

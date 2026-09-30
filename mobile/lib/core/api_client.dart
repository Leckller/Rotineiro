// core/api_client.dart
import 'package:dio/dio.dart';

Dio buildDio() {
  return Dio(BaseOptions(
    baseUrl: 'http://localhost:8080/v1',
    connectTimeout: const Duration(seconds: 10),
  ));
}
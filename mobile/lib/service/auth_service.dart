import 'package:dio/dio.dart';

class AuthService {
  AuthService(this._dio);
  final Dio _dio;

  Future<String> login(String email, String senha) async {
    final response = await _dio.post(
      "/login",
      data: {"email": email, "password": senha},
    );
    return response.data['token'] as String;
  }
}

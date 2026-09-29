import 'package:dio/dio.dart';
import 'package:mobile/core/auth_interceptor.dart';
import 'package:mobile/core/token_storage.dart';
import 'package:mobile/providers/auth_provider.dart';

final storage = TokenStorage();
final auth = AuthProvider(storage);

Dio dio = Dio(BaseOptions(
  baseUrl: 'http://localhost:8080/v1',
  connectTimeout: const Duration(seconds: 120),
))..interceptors.add(AuthInterceptor(storage, auth.logout));

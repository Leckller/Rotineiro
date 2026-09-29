import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/core/token_storage.dart';

class AuthInterceptor extends Interceptor {
  AuthInterceptor(this._storage, this._onUnauthorized);
  final TokenStorage _storage;
  final VoidCallback _onUnauthorized;

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    final token = await _storage.read();
    if (token != null) {
      options.headers['Authorization'] = 'Bearer $token';
    }
    handler.next(options);
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    if (err.response?.statusCode == 401) {
      _onUnauthorized();
    }
    handler.next(err);
  }
}
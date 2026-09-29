import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/core/token_storage.dart';
import 'package:mobile/service/auth_service.dart';

enum AuthStatus { authenticated, unauthenticated }

class AuthProvider extends ChangeNotifier {
  AuthProvider(this._storage, this._service);
  final AuthService _service;
  final TokenStorage _storage;

  AuthStatus status = AuthStatus.unauthenticated;
  bool loading = false;
  String? error;


  Future<void> checkAuth() async {
    final token = await _storage.read();
    status = token == null
        ? AuthStatus.unauthenticated
        : AuthStatus.authenticated;
    notifyListeners();
  }

  Future<void> login(String email, String password) async {
    loading = true;
    error = null;
    notifyListeners();
    try {
      final token = await _service.login(email, password);
      await _storage.save(token);
      status = AuthStatus.authenticated;
    } on DioException catch (e) {
      error = e.response?.data.toString();
    }
    loading = false;
    notifyListeners();
  }

  Future<void> logout() async {
    await _storage.clear();
    status = AuthStatus.unauthenticated;
    notifyListeners();
  }
}

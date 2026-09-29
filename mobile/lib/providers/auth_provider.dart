import 'package:flutter/material.dart';
import 'package:mobile/core/token_storage.dart';

enum AuthStatus { authenticated, unauthenticated }

class AuthProvider extends ChangeNotifier {
  AuthProvider(this._storage);
  final TokenStorage _storage;

  AuthStatus status = AuthStatus.unauthenticated;

  // chamado uma vez ao abrir o app
  Future<void> checkAuth() async {
    final token = await _storage.read();
    status = token == null
        ? AuthStatus.unauthenticated
        : AuthStatus.authenticated;
    notifyListeners();
  }

  Future<void> login(String token) async {
    await _storage.save(token);
    status = AuthStatus.authenticated;
    notifyListeners();
  }

  Future<void> logout() async {
    await _storage.clear();
    status = AuthStatus.unauthenticated;
    notifyListeners();
  }
}
import 'package:flutter/material.dart';

class AppNavibar extends StatelessWidget {
  const AppNavibar({super.key, required this.currentRoute});

  final String currentRoute;

  static const _routes = [
    '/home',
    '/historico',
    '/adicionar',
    '/dashboard',
    '/perfil',
  ];

  int get _currentIndex {
    final index = _routes.indexOf(currentRoute);
    return index == -1 ? 0 : index;
  }

  void _onDestinationSelected(BuildContext context, int index) {
    final route = _routes[index];
    if (route == currentRoute) return; // já está na tela

    Navigator.of(context).pushReplacementNamed(route);
  }

  @override
  Widget build(BuildContext context) {
    return NavigationBar(
      selectedIndex: _currentIndex,
      onDestinationSelected: (index) => _onDestinationSelected(context, index),
      destinations: const [
        NavigationDestination(
          icon: Icon(Icons.home_outlined),
          selectedIcon: Icon(Icons.home),
          label: 'Início',
        ),
        NavigationDestination(
          icon: Icon(Icons.history_outlined),
          selectedIcon: Icon(Icons.history),
          label: 'Histórico',
        ),
        NavigationDestination(
          icon: Icon(Icons.add_circle_outline),
          selectedIcon: Icon(Icons.add_circle),
          label: 'Adicionar',
        ),
        NavigationDestination(
          icon: Icon(Icons.dashboard_outlined),
          selectedIcon: Icon(Icons.dashboard_rounded),
          label: 'Dashboard',
        ),
        NavigationDestination(
          icon: Icon(Icons.person_outline),
          selectedIcon: Icon(Icons.person),
          label: 'Perfil',
        ),
      ],
    );
  }
}
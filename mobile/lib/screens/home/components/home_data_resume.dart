import 'package:flutter/material.dart';

class HomeDataResume extends StatelessWidget {
  HomeDataResume({super.key});

  final actualDate = DateTime.now();

  static const _diasSemana = [
    'Segunda',
    'Terça',
    'Quarta',
    'Quinta',
    'Sexta',
    'Sábado',
    'Domingo',
  ];

  IconData getHourIcon() {
    if (actualDate.hour >= 0 && actualDate.hour < 6) {
      return Icons.nightlight;
    }
    if (actualDate.hour > 19) {
      return Icons.nightlight_outlined;
    }
    if (actualDate.hour > 12) {
      return Icons.sunny;
    }
    return Icons.wb_sunny_outlined;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsetsDirectional.symmetric(horizontal: 16, vertical: 16),
      decoration: const BoxDecoration(
        borderRadius: BorderRadius.all(Radius.circular(10)),
        gradient: LinearGradient(
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
          colors: [
            Color.fromARGB(255, 132, 84, 246), // Top left color
            Color.fromARGB(255, 108, 45, 255), // Top left color
          ],
        ),
         boxShadow: [
          BoxShadow(
            color: Color.fromARGB(150, 132, 84, 246),
            blurRadius: 16,
            offset: Offset(0, 8),
          ),
        ],
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          DefaultTextStyle(
            style: const TextStyle(color: Colors.white),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              spacing: 12,
              children: [
                Text("HOJE"),
                Text(
                  "${_diasSemana[actualDate.weekday - 1]}, ${actualDate.day} ${actualDate.month}",
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.w600),
                ),
                Row(
                  spacing: 30,
                  children: [
                    Column(
                      children: [
                        Text(
                          "3",
                          style: TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        Text("Rotinas", style: TextStyle(fontSize: 10)),
                      ],
                    ),
                    Column(
                      children: [
                        Text(
                          "9",
                          style: TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        Text("Tarefas", style: TextStyle(fontSize: 10)),
                      ],
                    ),
                    Column(
                      children: [
                        Text(
                          "10",
                          style: TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        Text(
                          "Dias consecutivos",
                          style: TextStyle(fontSize: 10),
                        ),
                      ],
                    ),
                  ],
                ),
              ],
            ),
          ),
          CircleAvatar(
            backgroundColor: Colors.white,
            child: Icon(getHourIcon()),
          ),
        ],
      ),
    );
  }
}

import 'package:flutter/material.dart';

class HomeDataResume extends StatelessWidget {
  HomeDataResume({super.key});

  final actualDate = DateTime.now();

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsetsDirectional.symmetric(horizontal: 8, vertical: 8),
      child: Column(
        children: [
          Text("HOJE"),
          Text("Segunda, ${actualDate.day} ${actualDate.month}", style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600),),
          Row(
            children: [
              Column(
                children: [
                  Text(
                    "3",
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600),
                  ),
                  Text("Rotinas"),
                ],
              ),
              Column(
                children: [
                  Text(
                    "9",
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600),
                  ),
                  Text("Tarefas"),
                ],
              ),
              Column(
                children: [
                  Text(
                    "10",
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600),
                  ),
                  Text("Dias consecutivos"),
                ],
              ),
            ],
          ),
        ],
      ),
    );
  }
}

import 'package:flutter/material.dart';

class HomeHeader extends StatelessWidget {
  const HomeHeader({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text("Boa dia, Ruy!"),
            Text(
              "Minhas Rotinas",
              style: TextStyle(fontWeight: FontWeight.w700, fontSize: 24),
            ),
          ],
        ),
        CircleAvatar(
          backgroundColor: Colors.deepPurple,
          child: Text("R", style: TextStyle(color: Colors.white)),
        ),
      ],
    );
  }
}

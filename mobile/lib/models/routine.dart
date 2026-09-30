class Routine {
  final int id;
  final String title;
  final String description;

  Routine({required this.id, required this.title, required this.description});

  factory Routine.fromJson(Map<String, dynamic> json) {
    return Routine(
      id: json['id'],
      title: json['name'],
      description: json['description'],
    );
  }
}

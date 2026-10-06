class Routine {
  int id;
  String title;
  String description;

  Routine({required this.id, required this.title, required this.description});

  factory Routine.fromJson(Map<String, dynamic> json) {
    return Routine(
      id: json['ID'],
      title: json['Title'],
      description: json['Description'],
    );
  }

}

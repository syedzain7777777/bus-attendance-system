import 'dart:async';

class ApiService {
  Future<String> sendScan(String busId, String action) async {
    await Future.delayed(const Duration(seconds: 1));
    return "Bus $busId $action recorded successfully";
  }
}
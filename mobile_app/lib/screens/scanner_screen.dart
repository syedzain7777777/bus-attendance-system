import 'package:flutter/material.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import '../services/api_service.dart';

class ScannerScreen extends StatefulWidget {
  final String action;

  const ScannerScreen({super.key, required this.action});

  @override
  State<ScannerScreen> createState() => _ScannerScreenState();
}

class _ScannerScreenState extends State<ScannerScreen> {
  bool isProcessing = false;

  void handleScan(String busId) async {
    if (isProcessing) return;

    setState(() {
      isProcessing = true;
    });

    final api = ApiService();
    final result = await api.sendScan(busId, widget.action);

    if (!mounted) return;

    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text("Success"),
        content: Text(result),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context);
              Navigator.pop(context);
            },
            child: const Text("OK"),
          )
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Scan for ${widget.action}"),
        backgroundColor:
            widget.action == "ENTRY" ? Colors.green : Colors.red,
      ),
      body: MobileScanner(
        onDetect: (barcodeCapture) {
          final barcodes = barcodeCapture.barcodes;

          if (barcodes.isNotEmpty) {
            final String? code = barcodes.first.rawValue;

            if (code != null) {
              handleScan(code);
            }
          }
        },
      ),
    );
  }
}
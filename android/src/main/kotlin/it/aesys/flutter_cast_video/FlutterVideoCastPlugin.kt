package it.aesys.flutter_cast_video

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformViewRegistry

/** FlutterVideoCastPlugin */
public class FlutterVideoCastPlugin: FlutterPlugin, ActivityAware {
  private lateinit var chromeCastFactory: ChromeCastFactory

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    chromeCastFactory = ChromeCastFactory(flutterPluginBinding.binaryMessenger)
    flutterPluginBinding
            .platformViewRegistry
            .registerViewFactory(
                    "ChromeCastButton",
                    chromeCastFactory
            )
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  // companion object {
  //   @JvmStatic
  //   fun registerWith(registrar: io.flutter.plugin.common.PluginRegistry.Registrar) {
  //     // Use the new API internally to maintain compatibility with old Flutter versions
  //     val plugin = FlutterVideoCastPlugin()
  //     val factory = ChromeCastFactory(registrar.messenger())
  //     factory.activity = registrar.activity()
  //     registrar
  //             .platformViewRegistry()
  //             .registerViewFactory(
  //                     "ChromeCastButton",
  //                     factory
  //             )
  //   }
  // }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    // Clean up resources if needed
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    chromeCastFactory.activity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {
    // The Activity is about to be recreated due to a config change.
    // This is called right before onDetachedFromActivity.
  }

  override fun onDetachedFromActivity() {
    // Clean up resources related to the Activity if needed
    chromeCastFactory.activity = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    // The Activity was recreated due to a config change.
    // This is called after onAttachedToActivity.
    chromeCastFactory.activity = binding.activity
  }
}

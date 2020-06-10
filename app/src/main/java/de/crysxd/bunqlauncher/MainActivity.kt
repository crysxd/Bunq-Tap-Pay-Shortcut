package de.crysxd.bunqlauncher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent("com.bunq.android.hce.PAY")
        safeStartActivity(intent) {
            AlertDialog.Builder(this)
                .setMessage(R.string.error_bunq_not_installed)
                .setPositiveButton(R.string.close) { _, _ ->
                    finish()
                }
                .setNeutralButton(R.string.install_bunq) { _, _ ->
                    val playStoreIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.bunq.android")
                    )
                    safeStartActivity(playStoreIntent) {
                        finish()
                    }
                }
                .show()
        }
    }

    private fun safeStartActivity(intent: Intent, fallback: () -> Unit = {}) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            finish()
        } else {
            fallback()
        }
    }
}
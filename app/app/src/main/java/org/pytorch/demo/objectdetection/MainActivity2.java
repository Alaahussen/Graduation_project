package org.pytorch.demo.objectdetection;
import android.media.MediaPlayer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private ImageView gifImageView;
    private TextView resultTextView;
    private Button startButton;
    private boolean isAnimationPlayed = false;
    Animation scale_up,scale_down;
    private static final int REQUEST_MICROPHONE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_MICROPHONE);
        } else {
            //setupSpeechRecognizer();
        }

        gifImageView = findViewById(R.id.gifImageView);
        startButton = findViewById(R.id.startButton);
        resultTextView = findViewById(R.id.resultTextView);
        scale_up= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scale_down= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        // Initialize SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                // Speech recognition is ready
            }

            @Override
            public void onBeginningOfSpeech() {
                // Speech input has started
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // RMS (Root Mean Square) dB value has changed
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // Audio buffer has been received
            }

            @Override
            public void onEndOfSpeech() {
                // Speech input has ended
            }
            @Override
            public void onError(int error) {
                // Speech recognition error occurred
                String errorMessage;
                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        errorMessage = "Audio recording error";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        errorMessage = "Client side error";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        errorMessage = "Network error";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        errorMessage = "Network timeout";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        errorMessage = "No match found";
                        break;
                    /*case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        errorMessage = "RecognitionService busy";
                        break;
                        */
                    case SpeechRecognizer.ERROR_SERVER:
                        errorMessage = "Server error";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        errorMessage = "No speech input";
                        break;
                    default:
                        errorMessage = "Start speaking";
                        break;
                }
                Toast.makeText(MainActivity2.this, "Speech recognition : " + errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                // Speech recognition results are available
                deletePhoto("img.jpg");
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String spokenSentence = matches.get(0);
                    resultTextView.setText(spokenSentence);

                    //String[] words = spokenSentence.split(" "); // Split the sentence into words
                    showGifImage(spokenSentence + ".gif");
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // Partial speech recognition results are available
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // Reserved for future use
            }
        });
        startButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startButton.startAnimation(scale_up);
                    start(view);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startButton.startAnimation(scale_down);
                    start(view);

                }
                return true;

            }

        });
        /*startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechRecognition();
                Animation animation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.button_click_animation);
                startButton.startAnimation(animation);

            }
        });
        */

    }

    public void start(View view) {

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        startSpeechRecognition();


    }
    @Override
    public void onBackPressed() {
        back(null);
    }
    public void back(View view) {
        Intent intent=new Intent(this,FirstActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    private void startSpeechRecognition() {
        if (!isAnimationPlayed) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            speechRecognizer.startListening(intent);
        } else {
            Toast.makeText(this, "Speech recognition is not available on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showGifImage(String gifFileName) {
        try {
            InputStream inputStream = getAssets().open("models/"+gifFileName);
            byte[] bytes = IOUtils.toByteArray(inputStream); // Make sure to import org.apache.commons.io.IOUtils

            Glide.with(this)
                    .asGif()
                    .load(bytes)
                    .listener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            // Stop the GIF animation after the first loop
                            resource.setLoopCount(1);
                            return false;
                        }
                    })
                    .into(gifImageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean deletePhoto(String fileName) {
        File file = new File(getFilesDir(), fileName);
        return file.delete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the SpeechRecognizer resources
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
            speechRecognizer = null;
        }
    }
}

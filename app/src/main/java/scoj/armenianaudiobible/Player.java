package scoj.armenianaudiobible;

import android.app.DownloadManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static java.lang.Integer.parseInt;


public class Player extends AppCompatActivity {

    MediaPlayer mediaPlayer = new MediaPlayer();

    private ImageButton play_button = null;
    private ImageButton prev_button = null;
    private ImageButton next_button = null;
    private ListView listView = null;
    String selected = "1";
    String url;
    Uri uri;
    String carent_file;
    String erg1;
    int cank;
    Long referance = Long.valueOf(0);
    DownloadManager downloadManager;

    int i;

    @Override
    public void onBackPressed() {
        mediaPlayer.reset();
        super.onBackPressed();
    }
    View.OnClickListener play = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.reset();
                    play_button.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    //listView.setEnabled(false);

                    //prev_button.setEnabled(false);
                    //next_button.setEnabled(false);
                    carent_file = Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageState()).toString() + "/" + erg1 + "_" + selected + ".mp3";
                    File f = new File(carent_file);
                    if (f.exists()) {
                        mediaPlayer.setDataSource(carent_file);

                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        listView.setEnabled(true);
                        prev_button.setEnabled(true);
                        next_button.setEnabled(true);
                        play_button.setImageResource(android.R.drawable.ic_media_pause);
                    } else {

                        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.getExternalStorageState(), erg1 + "_" + selected + ".mp3");
                        referance = downloadManager.enqueue(request);

                        carent_file = Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageState()).toString() + "/" + erg1 + "_" + selected + ".mp3";

                        //todo request.setDestinationInExternalFilesDir( Environment.DIRECTORY_DOWNLOADS,"a");
                        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        //if(Long.valueOf(0) == referance){play_button.performClick();referance = Long.valueOf(0);}

                    }

                }


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Inetrnet error", Toast.LENGTH_SHORT).show();
            }
        }

    };
    View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int a = Integer.parseInt(selected);
            if (a == cank) {
                selected = String.valueOf(1);
            } else {
                selected = String.valueOf(a + 1);
            }
            if (mediaPlayer.isPlaying()) play_button.performClick();
            url = "https://www.derekprincearmenia.com/audiofiles/hy/" + erg1 + "/" + selected + ".mp3";
            play_button.performClick();


        }
    };
    View.OnClickListener prev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int a = Integer.parseInt(selected);
            if (a == 1) {
                selected = String.valueOf(cank);
            } else {
                selected = String.valueOf(a - 1);
            }
            if (mediaPlayer.isPlaying()) play_button.performClick();
            url = "https://www.derekprincearmenia.com/audiofiles/hy/" + erg1 + "/" + selected + ".mp3";
            play_button.performClick();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        play_button = (ImageButton) findViewById(R.id.play);
        prev_button = (ImageButton) findViewById(R.id.prev);
        next_button = (ImageButton) findViewById(R.id.next);
        listView = (ListView) findViewById(R.id.listView);


        Bundle bundle = getIntent().getExtras();
        String glux = bundle.getString("glux");
        String erg = bundle.getString("erg");
        String cucak = bundle.getString("cucak");

        erg1 = erg;

        cank = parseInt(cucak);
        String arr[] = new String[cank];
        for (i = 0; i < cank; i++) {
            arr[i] = String.valueOf(i + 1);
        }

        url = "https://www.derekprincearmenia.com/audiofiles/hy/" + erg + "/" + selected + ".mp3";


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);


        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(glux);
        play_button.setOnClickListener(play);
        next_button.setOnClickListener(next);
        prev_button.setOnClickListener(prev);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selected = String.valueOf(position + 1);


                if (mediaPlayer.isPlaying()) play_button.performClick();
                url = "https://www.derekprincearmenia.com/audiofiles/hy/" + erg1 + "/" + selected + ".mp3";
                play_button.performClick();

            }

        });
    }
}





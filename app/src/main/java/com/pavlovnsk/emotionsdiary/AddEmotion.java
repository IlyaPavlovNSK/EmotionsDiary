package com.pavlovnsk.emotionsdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddEmotion extends AppCompatActivity {

    private final int PICK_IMAGE = 2;

    private EditText addName;
    private EditText addDescription;
    private ImageView addPic;
    private Disposable disposable;

    @Inject
    AppRoomDataBase db;
    @Inject Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(this)).build();
        component.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emotion);

        addName = findViewById(R.id.et_emotion_name);
        addDescription = findViewById(R.id.et_emotion_description);
        addPic = findViewById(R.id.add_emotion_pic);
        Button addEmotion = findViewById(R.id.btn_add_emotion);

        addPic.setOnClickListener(addPicListener);
        addEmotion.setOnClickListener(addEmotionListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        db.close();
    }

    private ImageView.OnClickListener addPicListener = view -> addNewEmotionImage();

    private Button.OnClickListener addEmotionListener = view -> addEmotionInDataBase();

    private void addEmotionInDataBase() {
        if (addName.getText().length() != 0 && addDescription.getText().length() != 0 && addPic.getDrawable() != null) {

            final String bitmapAdress = Utils.saveToInternalStorage(((BitmapDrawable) addPic.getDrawable()).getBitmap(), context);
            final long date = new Date().getTime();

            EmotionForItem emotionForItem = new EmotionForItem(addName.getText().toString(), "0 %", addDescription.getText().toString(), date, bitmapAdress);
            disposable = Completable.fromAction(() -> db.emotionForItemDao().addEmotionItem(emotionForItem))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            startActivity(new Intent(AddEmotion.this, MainActivity.class));
        } else {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewEmotionImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                try {
                    final Uri fileUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(fileUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    addPic.setImageBitmap(selectedImage);
                    addPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } catch (FileNotFoundException e) {
                    e.getStackTrace();
                }
            }
        }
    }
}

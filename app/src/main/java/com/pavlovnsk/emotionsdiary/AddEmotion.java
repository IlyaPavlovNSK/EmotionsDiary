package com.pavlovnsk.emotionsdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

public class AddEmotion extends AppCompatActivity {

    private final int PICK_IMAGE = 2;

    private EditText addName;
    private EditText addDescription;
    private Button addEmotion;
    private ImageView addPic;

    @Inject
    DataBaseHelper dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(this)).build();
        component.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emotion);

        addName = findViewById(R.id.et_emotion_name);
        addDescription = findViewById(R.id.et_emotion_description);
        addPic = findViewById(R.id.add_emotion_pic);
        addEmotion = findViewById(R.id.btn_add_emotion);

        addPic.setOnClickListener(addPicListener);
        addEmotion.setOnClickListener(addEmotionListener);
    }

    private ImageView.OnClickListener addPicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addNewEmotionImage();
        }
    };

    private Button.OnClickListener addEmotionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addEmotionInDataBase();
        }
    };

    private void addEmotionInDataBase() {
        if (addName.getText().length() != 0 && addDescription.getText().length() != 0 && addPic.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) addPic.getDrawable()).getBitmap();

            EmotionItem emotionItem = new EmotionItem();
            emotionItem.setEmotionName(addName.getText().toString().trim());
            emotionItem.setDescription(addDescription.getText().toString().trim());
            emotionItem.setEmotionLevel("0 %");
            emotionItem.setEmotionPic(bitmap);

            dataBase.addEmotionItem(emotionItem);

            startActivity(new Intent(AddEmotion.this, ListEmotionsItemActivity.class));
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

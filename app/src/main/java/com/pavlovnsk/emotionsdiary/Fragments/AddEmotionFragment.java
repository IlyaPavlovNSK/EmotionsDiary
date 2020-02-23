package com.pavlovnsk.emotionsdiary.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pavlovnsk.emotionsdiary.Adapters.DaggerEmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class AddEmotionFragment extends Fragment {

    private final int PICK_IMAGE = 2;

    private EditText addName;
    private EditText addDescription;
    private Button addEmotion;
    private ImageView addPic;

    @Inject
    DataBaseHelper dataBase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(getContext())).build();
        component.inject(this);

        View view = inflater.inflate(R.layout.fragment_add_emotion, container, false);

        addName = view.findViewById(R.id.et_emotion_name);
        addDescription = view.findViewById(R.id.et_emotion_description);
        addPic = view.findViewById(R.id.add_emotion_pic);
        addEmotion = view.findViewById(R.id.btn_add_emotion);

        addPic.setOnClickListener(addPicListener);
        addEmotion.setOnClickListener(addEmotionListener);

        return view;
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

            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmotionsFragment()).commit();
        } else {
            Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_SHORT).show();
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
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(fileUri);
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

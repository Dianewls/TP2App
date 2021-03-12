package com.firida_diane.tp2app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;

    private final String texteInit = "Cliquez sur le bouton « Calculer l'IMC » pour obtenir un résultat.";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        envoyer = (Button)findViewById(R.id.btnimc);
        reset = (Button)findViewById(R.id.btnreset);
        taille = (EditText)findViewById(R.id.taille);
        poids = (EditText)findViewById(R.id.poids);
        commentaire = (CheckBox)findViewById(R.id.checkBox);
        group = (RadioGroup)findViewById(R.id.group);
        result = (TextView)findViewById(R.id.result);

        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  on récupère la taille
                String t = taille.getText().toString();
                // On récupère le poids
                String p = poids.getText().toString();
                float tValue = Float.valueOf(t);

                // Puis on vérifie que la taille est cohérente
                if(tValue <= 0)
                    Toast.makeText(MainActivity.this, "La taille doit être positive", Toast.LENGTH_SHORT).show();
                else {
                    float pValue = Float.valueOf(p);
                    if(pValue <= 0)
                        Toast.makeText(MainActivity.this, "Le poids doit etre positif", Toast.LENGTH_SHORT).show();
                    else {
                        // Si l'utilisateur a indiqué que la taille était en centimètres
                        // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                        if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) tValue = tValue / 100;
                        float imc = pValue / (tValue * tValue);
                        String resultat="Votre IMC est " + imc+" . ";
                        if(commentaire.isChecked()) resultat =resultat+ interpreteIMC(imc);

                        result.setText(resultat);
                    }
                }
            }



        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poids.getText().clear();
                taille.getText().clear();
                result.setText(texteInit);

            }
        });
        commentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    result.setText(texteInit);
                }


            }
        });

        //Affichage avec addTextChangedListener

        /*taille.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                result.setText(texteInit);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        poids.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                result.setText(texteInit);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        // Avec un Listener

        taille.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Toast.makeText(MainActivity.this, "keyCode"+keyCode, Toast.LENGTH_SHORT).show();
                if(keyCode==56){
                   RadioButton b=findViewById(R.id.radio_metre);
                   b.setChecked(true);
                }
                result.setText(texteInit);

                return false;
            }
        });

        poids.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                result.setText(texteInit);

                return false;
            }
        });
    }


    private String interpreteIMC(float imc) {

        if(imc<16) {
            return "famine";
        }else if(imc>16.5 && imc<18.5){
            return "maigreur";
        }
        else if(imc>18.5 && imc<25){
            return "Corpulence normale";
        }
        else if(imc>=25 && imc<30){
            return "Surpoids";
        }
        else if(imc>=30 && imc<35){
            return "Obésité modérée";
        }
        else if(imc>=35 && imc<40){
            return "Obésité sévère";
        }
        else if(imc>=40){
            return "Obésité MORBIDE";
        }else{
            return "pas d'info sur votre imc";
        }

    }
}
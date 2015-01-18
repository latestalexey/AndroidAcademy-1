package com.ciandt.cursoandroid.worldwondersapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ciandt.cursoandroid.worldwondersapp.R;
import com.ciandt.cursoandroid.worldwondersapp.entity.User;
import com.ciandt.cursoandroid.worldwondersapp.infrastructure.Constants;
import com.ciandt.cursoandroid.worldwondersapp.manager.RegisterManager;

import java.util.Arrays;


public class RegisterActivity extends Activity {

    private EditText mEditTextNome;
    private EditText mEditTextEmail;
    private EditText mEditTextSenha;
    private EditText mEditTextConfirmcaoSenha;

    private Spinner mSpinnerPais;
    private AutoCompleteTextView mAutoCompleteTextViewIdioma;
    private RadioGroup mRadioGroupSexo;
    private CheckBox mCheckBoxNotificacoes;

    private Button mButtonEnviar;

    private String mSexo;
    private int mNotificacoes;

    private RegisterManager mRegisterManager;

    String[] mLanguageArray;

    ArrayAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterManager = new RegisterManager(this);

        LinearLayout registerLayout = (LinearLayout) findViewById(R.id.register_layout);

        mEditTextNome = (EditText) registerLayout.findViewById(R.id.edittext_register_nome);
        mEditTextEmail  = (EditText) registerLayout.findViewById(R.id.edittext_register_email);
        mEditTextSenha = (EditText) registerLayout.findViewById(R.id.edittext_register_senha);
        mEditTextConfirmcaoSenha = (EditText) registerLayout.findViewById(R.id.edittext_register_confirmacao_senha);

        mSpinnerPais = (Spinner) registerLayout.findViewById(R.id.spinner_register_pais);
        mAutoCompleteTextViewIdioma = (AutoCompleteTextView) registerLayout.findViewById(R.id.autocompletetextview_register_idioma);
        mRadioGroupSexo = (RadioGroup) registerLayout.findViewById(R.id.radiogroup_register_sexo);
        mCheckBoxNotificacoes = (CheckBox) registerLayout.findViewById(R.id.checkbox_register_notificacoes);

        mButtonEnviar = (Button) registerLayout.findViewById(R.id.button_register_enviar);

        if(getIntent().hasExtra(Constants.Bundle.BUNDLE_USER_EMAIL)){
            mEditTextEmail.setText(getIntent().getStringExtra(Constants.Bundle.BUNDLE_USER_EMAIL));
        }


        setListeners();

    }

    private void setListeners() {

        mSpinnerPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 setIdioma(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mButtonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = mEditTextNome.getText().toString();
                String email = mEditTextEmail.getText().toString();
                String senha = mEditTextSenha.getText().toString();
                String pais = mSpinnerPais.getSelectedItem().toString();
                String idioma = mAutoCompleteTextViewIdioma.getText().toString();
                getNotificacoes();
                getSexo();


                if(validateFields()){

                    if(validatePassword()){

                        User user = new User();

                        user.setUserName(nome);
                        user.setUserEmail(email);
                        user.setUserCountry(pais);
                        user.setUserLanguage(idioma);
                        user.setUserGender(mSexo);
                        user.setUserNotification(mNotificacoes);
                        user.setUserPassword(senha);

                        if(mRegisterManager.registerUser(user)){
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.Bundle.BUNDLE_USER_EMAIL,email );
                            bundle.putString(Constants.Bundle.BUNDLE_USER_PASSWORD,senha );
                            intent.putExtras(bundle);

                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }
                }
            }
        });
    }


    private void setIdioma(String pais){

        if(Constants.Entity.User.TEXT_COUNTRY_BRASIL.equals(pais)){
            mLanguageArray = getApplication().getResources().getStringArray(R.array.array_language_br);
            mArrayAdapter  = getArrayAdapter(mLanguageArray);
        }else if(Constants.Entity.User.TEXT_COUNTRY_CANADA.equals(pais)){
            mLanguageArray = getApplication().getResources().getStringArray(R.array.array_language_ca);
            mArrayAdapter = getArrayAdapter(mLanguageArray);
        }else if(Constants.Entity.User.TEXT_COUNTRY_CHINA.equals(pais)){
            mLanguageArray = getApplication().getResources().getStringArray(R.array.array_language_ch);
            mArrayAdapter  = getArrayAdapter(mLanguageArray);
        }

        mAutoCompleteTextViewIdioma.setAdapter(mArrayAdapter);

    }

    private ArrayAdapter getArrayAdapter(String[] languageArray){

        return new ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line ,
                Arrays.asList(languageArray));
    }
    private void getSexo(){

        switch(mRadioGroupSexo.getCheckedRadioButtonId()){

            case R.id.radiobutton_register_masculino:
                mSexo = Constants.Entity.User.INDICATOR_GENDER_MALE;
                break;

            case R.id.radiobutton_register_feminino:
                mSexo = Constants.Entity.User.INDICATOR_GENDER_FEMALE;
                break;
        }
    }

    private void getNotificacoes(){

        if(mCheckBoxNotificacoes.isChecked()){
            mNotificacoes = Constants.Entity.User.INDICATOR_NOTIFICATION_ON;
        }else{
            mNotificacoes = Constants.Entity.User.INDICATOR_NOTIFICATION_OFF;
        }
    }

    private boolean validateFields(){

        if(mEditTextNome.getText().toString().isEmpty()){

            mEditTextNome.setError(getResources().getString(R.string.erro_validacao));
            return Boolean.FALSE;
        }

        if(mEditTextEmail.getText().toString().isEmpty()){

            mEditTextEmail.setError(getResources().getString(R.string.erro_validacao));
            return Boolean.FALSE;
        }

        if(mEditTextSenha.getText().toString().isEmpty()){

            mEditTextSenha.setError(getResources().getString(R.string.erro_validacao));
            return Boolean.FALSE;
        }

        if(mEditTextConfirmcaoSenha.getText().toString().isEmpty()){

            mEditTextConfirmcaoSenha.setError(getResources().getString(R.string.erro_validacao));
            return Boolean.FALSE;
        }

        if(mAutoCompleteTextViewIdioma.getText().toString().isEmpty()){

            mAutoCompleteTextViewIdioma.setError(getResources().getString(R.string.erro_validacao));
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }


    private boolean validatePassword(){

        if(mEditTextSenha.getText().toString().equals(mEditTextConfirmcaoSenha.getText().toString())){
             return Boolean.TRUE;
        }else{
            mEditTextConfirmcaoSenha.setError(getResources().getString(R.string.erro_validacao_senha));
            return Boolean.FALSE;
        }

    }
}

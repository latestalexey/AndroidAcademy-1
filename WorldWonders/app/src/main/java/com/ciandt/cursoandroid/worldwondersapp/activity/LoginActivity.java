package com.ciandt.cursoandroid.worldwondersapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ciandt.cursoandroid.worldwondersapp.R;
import com.ciandt.cursoandroid.worldwondersapp.infrastructure.Constants;
import com.ciandt.cursoandroid.worldwondersapp.manager.LoginManager;


public class LoginActivity extends ActionBarActivity {

    private EditText mEditTextUsuario;
    private EditText mEditTextSenha;
    private Button mButtonLogin;
    private Button mButtonRegister;

    private LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // getSupportActionBar().hide();

        mLoginManager = new LoginManager(this);

        LinearLayout mLoginLayout = (LinearLayout) findViewById(R.id.login_layout);

        mEditTextUsuario = (EditText) mLoginLayout.findViewById(R.id.edittext_login_email);
        mEditTextSenha = (EditText) mLoginLayout.findViewById(R.id.edittext_login_senha);
        mButtonLogin = (Button) mLoginLayout.findViewById(R.id.button_login_entrar);
        mButtonRegister = (Button) mLoginLayout.findViewById(R.id.button_login_registrar);

        setListeners();
    }

    private void setListeners() {

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = mEditTextUsuario.getText().toString();
                String senha = mEditTextSenha.getText().toString();

                if (validateFields(usuario, senha)) {

                    //  Toast.makeText(getApplicationContext(),usuario+":"+senha,Toast.LENGTH_LONG).show();
                    loginUser(usuario, senha);
                }
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(Constants.Bundle.BUNDLE_USER_EMAIL, mEditTextUsuario.getText().toString());
                intent.putExtras(bundle);
                startActivityForResult(intent, Constants.RequestCode.LOGIN_ACTIVITY);

            }
        });
    }

    private boolean validateFields(String usuario, String senha) {

        if (usuario.isEmpty()) {
            mEditTextUsuario.setError(getResources().getString(R.string.erro_validacao));
            return false;
        }

        if (senha.isEmpty()) {
            mEditTextSenha.setError(getResources().getString(R.string.erro_validacao));
            return false;
        }

        return true;
    }

    private void loginUser(String userEmail, String userPassword) {

        if (mLoginManager.loginUser(userEmail, userPassword) != null) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {

            Toast.makeText(getApplicationContext(), getResources().getText(R.string.login_validacao), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == Constants.RequestCode.LOGIN_ACTIVITY) {
                if (data != null) {

                     loginUser(data.getStringExtra(Constants.Bundle.BUNDLE_USER_EMAIL),
                            data.getStringExtra(Constants.Bundle.BUNDLE_USER_PASSWORD));

                }
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.register_cancelado),Toast.LENGTH_LONG).show();
        }
    }
}

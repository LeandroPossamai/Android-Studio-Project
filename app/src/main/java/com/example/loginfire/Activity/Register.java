package com.example.loginfire.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginfire.MainActivity;
import com.example.loginfire.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.text.method.PasswordTransformationMethod;

public class Register extends AppCompatActivity {

    private EditText edt_email_register;
    private EditText edt_senha_register;
    private EditText edt_confirmar_senha_register;
    private CheckBox ckb_mostrar_senha_register;
    private Button btn_registrar_register;
    private Button btn_register;
    private ProgressBar loginProgressBar_register;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        edt_email_register = findViewById(R.id.edt_email_register);
        edt_senha_register = findViewById(R.id.edt_senha_register);
        edt_confirmar_senha_register = findViewById(R.id.edt_confirmar_senha_register);
        ckb_mostrar_senha_register = findViewById(R.id.ckb_mostrar_senha_register);
        btn_registrar_register = findViewById(R.id.btn_registrar_register);
        btn_register = findViewById(R.id.btn_register);
        loginProgressBar_register = findViewById(R.id.loginProgressBar_register);


        btn_registrar_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerEmail = edt_email_register.getText().toString();
                String senha = edt_senha_register.getText().toString();
                String confirmarSenha = edt_confirmar_senha_register.getText().toString();

                if (!TextUtils.isEmpty(registerEmail) || !TextUtils.isEmpty(senha) || !TextUtils.isEmpty(confirmarSenha)){
                    if (senha.equals(confirmarSenha)){
                        loginProgressBar_register.setVisibility(View.VISIBLE);


                        ckb_mostrar_senha_register.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                if (isChecked) {
                                    // Se o CheckBox está marcado, mostra a senha
                                    edt_senha_register.setTransformationMethod(null);
                                    edt_confirmar_senha_register.setTransformationMethod(null);
                                } else {
                                    // Se o CheckBox não está marcado, oculta a senha
                                    edt_senha_register.setTransformationMethod(new PasswordTransformationMethod());
                                    edt_confirmar_senha_register.setTransformationMethod(new PasswordTransformationMethod());
                                }
                            }
                        });









                        mAuth.createUserWithEmailAndPassword(registerEmail, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    abrirTelaPrincipal();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(Register.this, error, Toast.LENGTH_SHORT).show();
                                }
                                loginProgressBar_register.setVisibility(View.INVISIBLE);
                            }
                        });

                    } else {
                        Toast.makeText(Register.this, "A senha deve ser a mesma em ambos campos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica de login, se necessário
            }
        });





    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
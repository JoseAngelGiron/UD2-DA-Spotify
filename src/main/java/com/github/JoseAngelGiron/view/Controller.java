package com.github.JoseAngelGiron.view;



import com.github.JoseAngelGiron.App;

import java.io.IOException;

public abstract class Controller {
    App app;
    public void setApp(App app){
        this.app=app;
    }

    public abstract void onOpen(Object input, Object input2) throws IOException;
    public abstract void onClose(Object output);
}

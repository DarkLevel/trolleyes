package net.daw.generadores;

import net.daw.bean.specificBeanImplementation.UsuarioBean;

public class Generadorusuarios {
    public UsuarioBean generar() throws Exception {
        String[] arrayDni = {"codigo_1", "codigo_2", "codigo_3", "codigo_4", "codigo_5", "codigo_6", "codigo_7", "codigo_8", "codigo_9", "codigo_10"};
        String[] arrayNombre = {"nombre_1", "nombre_2", "nombre_3", "nombre_4", "nombre_5", "nombre_6", "nombre_7", "nombre_8", "nombre_9", "nombre_10"};
        String[] arrayApe1 = {"ape1_1", "ape1_2", "ape1_3", "ape1_4", "ape1_5", "ape1_6", "ape1_7", "ape1_8", "ape1_9", "ape1_10"};
        String[] arrayApe2 = {"ape2_1", "ape2_2", "ape2_3", "ape2_4", "ape2_5", "ape2_6", "ape2_7", "ape2_8", "ape2_9", "ape2_10"};
        String[] arrayLogin = {"login_1", "login_2", "login_3", "login_4", "login_5", "login_6", "login_7", "login_8", "login_9", "login_10"};
        String[] arrayPass = {"pass_1", "pass_2", "pass_3", "pass_4", "pass_5", "pass_6", "pass_7", "pass_8", "pass_9", "pass_10"};
        Integer[] arrayId_tipousuario = {1, 2, 3, 4, 5};

        int random_1 = (int)(Math.random() * ((8 - 0) + 1)) + 0;
        int random_2 = (int)(Math.random() * ((8 - 0) + 1)) + 0;
        int random_3 = (int)(Math.random() * ((8 - 0) + 1)) + 0;
        int random_4 = (int)(Math.random() * ((8 - 0) + 1)) + 0;
        int random_5 = (int)(Math.random() * ((8 - 0) + 1)) + 0;
        int random_6 = (int)(Math.random() * ((8 - 0) + 1)) + 0;
        int random_7 = (int)(Math.random() * ((3 - 0) + 1)) + 0;

        UsuarioBean oUsuarioBean = new UsuarioBean();
        
        oUsuarioBean.setDni(arrayDni[random_1]);
        oUsuarioBean.setNombre(arrayNombre[random_2]);
        oUsuarioBean.setApe1(arrayApe1[random_3]);
        oUsuarioBean.setApe2(arrayApe2[random_4]);
        oUsuarioBean.setLogin(arrayLogin[random_5]);
        oUsuarioBean.setPass(arrayPass[random_6]);
        oUsuarioBean.setId_tipoUsuario(arrayId_tipousuario[random_7]);

        return oUsuarioBean;
    }
}

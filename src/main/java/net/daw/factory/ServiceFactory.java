package net.daw.factory;

import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specificBeanImplementation.ReplyBean;
import net.daw.bean.specificBeanImplementation.UsuarioBean;
import net.daw.service.specificServiceImplementation.CarritoService;
import net.daw.service.specificServiceImplementation.FacturaService;
import net.daw.service.specificServiceImplementation.LineaService;
import net.daw.service.specificServiceImplementation.ProductoService;
import net.daw.service.specificServiceImplementation.TipoproductoService;
import net.daw.service.specificServiceImplementation.TipousuarioService;
import net.daw.service.specificServiceImplementation.UsuarioService;

public class ServiceFactory {

    public static ReplyBean executeService(HttpServletRequest oRequest) throws Exception {

        String ob = oRequest.getParameter("ob");
        String op = oRequest.getParameter("op");
        ReplyBean oReplyBean;

        int id_tipoProducto = 0;
        if (oRequest.getSession().getAttribute("user") != null) {
            UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
            id_tipoProducto = oUsuarioBean.getObj_tipoUsuario().getId();
        }

        switch (id_tipoProducto) {
            case 1:
                switch (ob) {
                    case "tipousuario":
                        TipousuarioService oTipousuarioService = new TipousuarioService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oTipousuarioService.get();
                                break;
                            case "create":
                                oReplyBean = oTipousuarioService.create();
                                break;
                            case "update":
                                oReplyBean = oTipousuarioService.update();
                                break;
                            case "remove":
                                oReplyBean = oTipousuarioService.remove();
                                break;
                            case "getcount":
                                oReplyBean = oTipousuarioService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oTipousuarioService.getpage();
                                break;
                            case "filldatabase":
                                oReplyBean = oTipousuarioService.filldatabase();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "usuario":
                        UsuarioService oUsuarioService = new UsuarioService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oUsuarioService.get();
                                break;
                            case "getprofile":
                                oReplyBean = oUsuarioService.getprofile();
                                break;
                            case "create":
                                oReplyBean = oUsuarioService.create();
                                break;
                            case "update":
                                oReplyBean = oUsuarioService.update();
                                break;
                            case "updateprofile":
                                oReplyBean = oUsuarioService.updateprofile();
                                break;
                            case "remove":
                                oReplyBean = oUsuarioService.remove();
                                break;
                            case "getcount":
                                oReplyBean = oUsuarioService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oUsuarioService.getpage();
                                break;
                            case "filldatabase":
                                oReplyBean = oUsuarioService.filldatabase();
                                break;
                            case "logout":
                                oReplyBean = oUsuarioService.logout();
                                break;
                            case "check":
                                oReplyBean = oUsuarioService.check();
                                break;
                            case "changepass":
                                oReplyBean = oUsuarioService.changepass();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "factura":
                        FacturaService oFacturaService = new FacturaService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oFacturaService.get();
                                break;
                            case "create":
                                oReplyBean = oFacturaService.create();
                                break;
                            case "update":
                                oReplyBean = oFacturaService.update();
                                break;
                            case "remove":
                                oReplyBean = oFacturaService.remove();
                                break;
                            case "getcount":
                                oReplyBean = oFacturaService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oFacturaService.getpage();
                                break;
                            case "getcountX":
                                oReplyBean = oFacturaService.getcountX();
                                break;
                            case "getpageX":
                                oReplyBean = oFacturaService.getpageX();
                                break;
                            case "getcountfacturasuser":
                                oReplyBean = oFacturaService.getcountfacturasuser();
                                break;
                            case "getpagefacturasuser":
                                oReplyBean = oFacturaService.getpagefacturasuser();
                                break;
                            case "filldatabase":
                                oReplyBean = oFacturaService.filldatabase();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "linea":
                        LineaService oLineaService = new LineaService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oLineaService.get();
                                break;
                            case "create":
                                oReplyBean = oLineaService.create();
                                break;
                            case "update":
                                oReplyBean = oLineaService.update();
                                break;
                            case "remove":
                                oReplyBean = oLineaService.remove();
                                break;
                            case "getcount":
                                oReplyBean = oLineaService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oLineaService.getpage();
                                break;
                            case "getcountX":
                                oReplyBean = oLineaService.getcountX();
                                break;
                            case "getpageX":
                                oReplyBean = oLineaService.getpageX();
                                break;
                            case "getcountlineauser":
                                oReplyBean = oLineaService.getcountlineauser();
                                break;
                            case "getpagelineauser":
                                oReplyBean = oLineaService.getpagelineauser();
                                break;
                            case "filldatabase":
                                oReplyBean = oLineaService.filldatabase();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "producto":
                        ProductoService oProductoService = new ProductoService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oProductoService.get();
                                break;
                            case "create":
                                oReplyBean = oProductoService.create();
                                break;
                            case "update":
                                oReplyBean = oProductoService.update();
                                break;
                            case "remove":
                                oReplyBean = oProductoService.remove();
                                break;
                            case "getcount":
                                oReplyBean = oProductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oProductoService.getpage();
                                break;
                            case "addimage":
                                oReplyBean = oProductoService.addimage();
                                break;
                            case "filldatabase":
                                oReplyBean = oProductoService.filldatabase();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tipoproducto":
                        TipoproductoService oTipoproductoService = new TipoproductoService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oTipoproductoService.get();
                                break;
                            case "create":
                                oReplyBean = oTipoproductoService.create();
                                break;
                            case "update":
                                oReplyBean = oTipoproductoService.update();
                                break;
                            case "remove":
                                oReplyBean = oTipoproductoService.remove();
                                break;
                            case "getcount":
                                oReplyBean = oTipoproductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oTipoproductoService.getpage();
                                break;
                            case "filldatabase":
                                oReplyBean = oTipoproductoService.filldatabase();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "carrito":
                        CarritoService oCarritoService = new CarritoService(oRequest);
                        switch (op) {
                            case "add":
                                oReplyBean = oCarritoService.add();
                                break;
                            case "reduce":
                                oReplyBean = oCarritoService.reduce();
                                break;
                            case "remove":
                                oReplyBean = oCarritoService.remove();
                                break;
                            case "empty":
                                oReplyBean = oCarritoService.empty();
                                break;
                            case "show":
                                oReplyBean = oCarritoService.show();
                                break;
                            case "buy":
                                oReplyBean = oCarritoService.buy();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Object doesn't exist");
                        break;
                }
                break;
            case 2:
                switch (ob) {
                    case "usuario":
                        UsuarioService oUsuarioService = new UsuarioService(oRequest);
                        switch (op) {
                            case "getprofile":
                                oReplyBean = oUsuarioService.getprofile();
                                break;
                            case "updateprofile":
                                oReplyBean = oUsuarioService.updateprofile();
                                break;
                            case "logout":
                                oReplyBean = oUsuarioService.logout();
                                break;
                            case "check":
                                oReplyBean = oUsuarioService.check();
                                break;
                            case "changepass":
                                oReplyBean = oUsuarioService.changepass();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "factura":
                        FacturaService oFacturaService = new FacturaService(oRequest);
                        switch (op) {
                            case "getcountfacturasuser":
                                oReplyBean = oFacturaService.getcountfacturasuser();
                                break;
                            case "getpagefacturasuser":
                                oReplyBean = oFacturaService.getpagefacturasuser();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "linea":
                        LineaService oLineaService = new LineaService(oRequest);
                        switch (op) {
                            case "getcountlineauser":
                                oReplyBean = oLineaService.getcountlineauser();
                                break;
                            case "getpagelineauser":
                                oReplyBean = oLineaService.getpagelineauser();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "producto":
                        ProductoService oProductoService = new ProductoService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oProductoService.get();
                                break;
                            case "getcount":
                                oReplyBean = oProductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oProductoService.getpage();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "carrito":
                        CarritoService oCarritoService = new CarritoService(oRequest);
                        switch (op) {
                            case "add":
                                oReplyBean = oCarritoService.add();
                                break;
                            case "reduce":
                                oReplyBean = oCarritoService.reduce();
                                break;
                            case "remove":
                                oReplyBean = oCarritoService.remove();
                                break;
                            case "empty":
                                oReplyBean = oCarritoService.empty();
                                break;
                            case "show":
                                oReplyBean = oCarritoService.show();
                                break;
                            case "buy":
                                oReplyBean = oCarritoService.buy();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Object doesn't exist");
                        break;
                }
                break;
            default:
                switch (ob) {
                    case "usuario":
                        UsuarioService oUsuarioService = new UsuarioService(oRequest);
                        switch (op) {
                            case "createuser":
                                oReplyBean = oUsuarioService.createuser();
                                break;
                            case "login":
                                oReplyBean = oUsuarioService.login();
                                break;
                            case "check":
                                oReplyBean = oUsuarioService.check();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Object doesn't exist");
                        break;
                }
                break;
        }
        return oReplyBean;
    }
}

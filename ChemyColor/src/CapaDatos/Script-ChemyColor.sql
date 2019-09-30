create table TipoContribuyente 
(
CodigoTipoContribuyente int primary key,
TipoContribuyente varchar(100)
);

create sequence CodigoTipoContribuyente1 minvalue 1 start with 1 cache 10;

insert into TipoContribuyente values ( CodigoTipoContribuyente1.nextval ,'Grande');
insert into TipoContribuyente values (CodigoTipoContribuyente1.nextval ,'Mediana');
insert into TipoContribuyente values (CodigoTipoContribuyente1.nextval ,'Peque�a');


create table TipoProveedores
(
CodTipoProv int primary key,
TipoProv varchar(50)
);

create sequence CodTipoProv minvalue 1 start with 1 cache 10;

create table Proveedores
(
CodigoProveedor int primary key,
Nombres varchar(100) UNIQUE,
NRC  varchar(100),
FechaIngreso varchar(12),
Saldo decimal(18,4),
Direccion varchar(600),
Email varchar(50),
Registro varchar(20),
Nit varchar(20),
DUI varchar(10),
Giro varchar(20),
Limite varchar(20),
CuentaPorPagar varchar(100),
CodigoTipoContribuyente int, foreign key (CodigoTipoContribuyente) references TipoContribuyente(CodigoTipoContribuyente),
CodTipoProv int, foreign key (CodTipoProv) references TipoProveedores(CodTipoProv)
);

create sequence CodigoProveedor minvalue 1 start with 1 cache 10;

create table TelefonosProveedor
(
CodigoProveedor int, foreign key (CodigoProveedor) references Proveedores(CodigoProveedor),
Telefono varchar(12)
);


create table Bodegas
(
CodigoBodega int primary key,
NombreBodega varchar(30) unique not null,
Direccion varchar(300)not null,
Email varchar(50) unique not null,
Encargado varchar(50) not null,
DarCredito integer,
PModPrec integer,
PFacSinExis integer,
DescMaxPorVend varchar(20),
SerieComprobante varchar(50),
DescMaxPermitido varchar(20),
Multicaja integer,
ImpRecAb integer,
Estado integer
);

create sequence CodigoBodega minvalue 1 start with 1 cache 10;

create table TelefonosPorBodega
(
CodigoBodega int, foreign key (CodigoBodega) references Bodegas(CodigoBodega),
Telefono varchar(15),
Orden int
);

create table TiposFacturas
(
CodigoTipoFact int primary key,
TipoFactura varchar(50)
);

create sequence CodigoTipoFact minvalue 1 start with 1 cache 10;

create table FacturacionBodega
(
CodigoBodega int, foreign key (CodigoBodega) references Bodegas(CodigoBodega),
CodigoTipoFact int, foreign key (CodigoTipoFact) references TiposFacturas(CodigoTipoFact)
);

create table FormasPago
(
CodigoFormaPago int primary key,
FormaPago varchar(50)
);

create sequence CodigoFormaPago minvalue 1 start with 1 cache 10;

create table FormasPagoPorBodega
(
CodigoBodega int, foreign key (CodigoBodega)  references Bodegas(CodigoBodega),
CodigoFormaPago int, foreign key (CodigoFormaPago) references FormasPago(CodigoFormaPago)
);

create table TiposMovimientos
(
CodTipoMov int primary key,
TipoMov varchar(50)
);

create sequence CodTipoMov minvalue 1 start with 1 cache 10;

create table IVA
(
CodigoIVA int primary key,
ValorIVA varchar(10)
);

create sequence CodigoIVA minvalue 1 start with 1 cache 10;

create table FacturasCompras
(
CodFactCompra int primary key,
Fecha varchar(12),
CodigoProveedor int, foreign key (CodigoProveedor)references Proveedores(CodigoProveedor),
NumeroComprobante varchar(100),
RegistroMes varchar(12),
RegistroAnnio varchar(12),
CodigoFormaPago int, foreign key (CodigoFormaPago) references FormasPago(CodigoFormaPago),
CodTipoMov int, foreign key (CodTipoMov) references TiposMovimientos(CodTipoMov),
NoRem varchar(100),
Observaciones varchar(300),
CodigoBodega int, foreign key (CodigoBodega) references Bodegas(CodigoBodega),
CodigoIVA int, foreign key (CodigoIva) references IVA(CodigoIva)
);


create table Grupos
(
CodigoGrupo int primary key,
NombreGrupo varchar(50),
Comision decimal(18,4)
);


create table Lineas
(
CodigoLinea int primary key,
CodigoGrupo int, foreign key (CodigoGrupo) references Grupos(CodigoGrupo),
NombreLineas varchar(50)
);

create table Articulos
(
CodigoArticulo int primary key,
codigoProductos varchar(100) not null,
NombreArticulo varchar(50) not null,
CodigoBarra varchar(25) not null,
ExistenciaMin int,
CodigoGrupo int, foreign key (CodigoGrupo) references Grupos(CodigoGrupo),
CodigoBodega int, foreign key (CodigoBodega) references Bodegas(CodigoBodega),
CodigoLinea int, foreign key (CodigoLinea) references Lineas(CodigoLinea),
Existencia int,
Utilidad decimal(18,4)
);

create table UnidadesMedida
(
CodigoArticulo int, foreign key (CodigoArticulo) references Articulos(CodigoArticulo),
UnidadMedida varchar(50)
);


create table PrecioPorArticulo
(
CodigoArticulo int, foreign key (CodigoArticulo) references Articulos(CodigoArticulo),
cantidad int,
Precio decimal(18,4)
);

create table DetalleFacturaCompra
(
CodFactCompra int, foreign key (CodFactCompra) references FacturasCompras(CodFactCompra),
Item int,
CodigoArticulo int, foreign key (CodigoArticulo) references Articulos(CodigoArticulo),
Descripcion varchar(200),
Cantidad int, 
Descuento varchar(20),
Valor decimal(18,4),
CuentaContable varchar(20)
);


create table TipoUsuarios
(
CodTipoUsuario int primary key,
TipoUsuario varchar(50) not null
);

create sequence tipousuario1 minvalue 1 start with 1 cache 10;

insert into TipoUsuarios values (CodTipoUsuario.nextval,'Administrador');
insert into TipoUsuarios values (CodTipoUsuario.nextval,'Jefe de Bodega');
insert into TipoUsuarios values (CodTipoUsuario.nextval,'Auxiliar de Bodega');



create table Usuarios
(
CodigoUsuario int primary key,
CodTipoUsuario int, foreign key (CodTipoUsuario) references TipoUsuarios(CodTipoUsuario),
NombreUsuario varchar(100) not null,
ApellidoUsuario varchar(100) not null,
Email varchar(100),
Usuario varchar(50) not null unique,
Contrasena varchar(100) not null,
Celular varchar(10),
Direccion varchar(300),
Estado integer
);

create sequence usuario1 minvalue 1 start with 1 cache 10;

--Para poner un usuario por default.
INSERT INTO 
	Usuarios 
VALUES 
	(1, 'ADMIN','ADMIN', 'admin@chemycolor.com', 'admin', 
	0x07E6E506A23ABD3E2F0205567EC06CA74D9E45B67D0A32AAF980A8182A91F67B,
	0x1BD5131FCEF59197DFBB8FB4D9523CFEB0864A4B523BBA8FDF0A8552F1284EA5, '0000-0000', 'Chemy',1);




--David 16-05-2019
CREATE TABLE HistorialFacturas
(
Codigo INT PRIMARY KEY,
NumFactura VARCHAR(25) NOT NULL,
cont_O_credit integer NOT NULL, --Variable para saber si es al contado o al credito. Contado = true y Credito = false
CantidadDiasCredit INT, --Variable para saber la cantidad de los dias si es al cr�dito
Estado integer NOT NULL, --Variable para saber si es entrada o salida. Entrada = false(0) y Salida = true(1)
CodigoBodega INT, FOREIGN KEY (CodigoBodega) REFERENCES Bodegas(CodigoBodega),
CodigoArticulo INT, FOREIGN KEY (CodigoArticulo) REFERENCES Articulos(CodigoArticulo),
Fecha date,
Factura VARCHAR(50),
CCF VARCHAR(50),
Ticket VARCHAR(50),
AjusteInventario VARCHAR(100),
CodigoProveedor INT, FOREIGN KEY (CodigoProveedor) REFERENCES Proveedores(CodigoProveedor),

--entradas simbolog�a = e
eUnidad INT,
ePrecio decimal(18,4),
eTotal decimal(18,4),

--salidas simbolog�a = o (Out en ingles)
oUnidad INT,
oPrecio decimal(18,4),
oTotal decimal(18,4),
oCliente VARCHAR(100),

--Saldos simbolog�a = s
sUnidad INT,
sCostoPromedio decimal(18,4),
sTotal decimal(18,4),
Detalle VARCHAR(1024)
);



CREATE TABLE DetallesFactura 
(
	Codigo INT, FOREIGN KEY (Codigo) REFERENCES HistorialFacturas(Codigo),
	Nit VARCHAR(25),
	NRC VARCHAR(50),
	Giro VARCHAR(50),
	Dui VARCHAR(25),
	Direccion VARCHAR(1024),
	IvaCreditoFiscal VARCHAR(25)
);






--04-06-2019 DAVID

CREATE TABLE UsuarioSesion
(
	Codigo INT PRIMARY KEY,
	Nombre VARCHAR(50)
);


INSERT INTO UsuarioSesion VALUES ('admin');


CREATE TABLE EmailRecuperador
(
	Codigo INT PRIMARY KEY,
	Email VARCHAR(100),
	Contrasennia VARCHAR(50)
);


INSERT INTO 
	EmailRecuperador 
VALUES 
	('prueba@chemy.com','admin');
GO





--Secuencias Septiembre 2019

CREATE SEQUENCE HistorialFacturaSeq
MINVALUE 1
MAXVALUE 9999999999999
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE SEQUENCE AjusteInventarioSeq
MINVALUE 1
MAXVALUE 9999999999999
START WITH 1
INCREMENT BY 1
CACHE 20;






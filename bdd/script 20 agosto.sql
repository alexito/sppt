SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[localidad]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[localidad](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](50) NULL,
 CONSTRAINT [PK_localidad] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[usuario]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[usuario](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](30) NULL,
	[apellido] [varchar](30) NULL,
	[cedula] [varchar](15) NULL,
	[clave] [varchar](15) NULL,
	[email] [varchar](50) NULL,
	[telefono] [varchar](30) NULL,
	[estado] [bit] NULL,
	[rol] [varchar](50) NULL,
	[id_aprobador] [int] NULL,
 CONSTRAINT [PK_usuario] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[distancia]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[distancia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[distancia] [float] NULL,
	[id_origen] [int] NULL,
	[id_destino] [int] NULL,
 CONSTRAINT [PK_distancia] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[solicitud]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[solicitud](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[f_salida] [datetime] NULL,
	[f_llegada] [datetime] NULL,
	[hospedaje] [varchar](100) NULL,
	[estado] [bit] NULL,
	[novedades] [varchar](100) NULL,
	[id_distancia] [int] NULL,
	[id_usuario_solicita] [int] NULL,
	[id_usuario_conductor] [int] NULL,
	[id_usuario_crea] [int] NULL,
 CONSTRAINT [PK_solicitud] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_usuario_usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[usuario]'))
ALTER TABLE [dbo].[usuario]  WITH CHECK ADD  CONSTRAINT [FK_usuario_usuario] FOREIGN KEY([id_aprobador])
REFERENCES [dbo].[usuario] ([id])
GO
ALTER TABLE [dbo].[usuario] CHECK CONSTRAINT [FK_usuario_usuario]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_distancia_localidad]') AND parent_object_id = OBJECT_ID(N'[dbo].[distancia]'))
ALTER TABLE [dbo].[distancia]  WITH CHECK ADD  CONSTRAINT [FK_distancia_localidad] FOREIGN KEY([id_origen])
REFERENCES [dbo].[localidad] ([id])
GO
ALTER TABLE [dbo].[distancia] CHECK CONSTRAINT [FK_distancia_localidad]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_distancia_localidad1]') AND parent_object_id = OBJECT_ID(N'[dbo].[distancia]'))
ALTER TABLE [dbo].[distancia]  WITH CHECK ADD  CONSTRAINT [FK_distancia_localidad1] FOREIGN KEY([id_destino])
REFERENCES [dbo].[localidad] ([id])
GO
ALTER TABLE [dbo].[distancia] CHECK CONSTRAINT [FK_distancia_localidad1]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_distancia]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_distancia] FOREIGN KEY([id_distancia])
REFERENCES [dbo].[distancia] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_distancia]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_usuario] FOREIGN KEY([id_usuario_solicita])
REFERENCES [dbo].[usuario] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_usuario]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_usuario1]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_usuario1] FOREIGN KEY([id_usuario_conductor])
REFERENCES [dbo].[usuario] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_usuario1]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_usuario2]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_usuario2] FOREIGN KEY([id_usuario_crea])
REFERENCES [dbo].[usuario] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_usuario2]

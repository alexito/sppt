SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[usuario]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[usuario](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](30) NOT NULL,
	[apellido] [varchar](30) NOT NULL,
	[cedula] [varchar](15) NULL,
	[clave] [varchar](15) NULL,
	[email] [varchar](50) NULL,
	[telefono] [varchar](30) NULL,
	[estado] [bit] NULL,
	[rol] [varchar](50) NULL,
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
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[solicitud]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[solicitud](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[origen] [varchar](50) NULL,
	[destino] [varchar](50) NULL,
	[f_salida] [datetime] NULL,
	[f_llegada] [datetime] NULL,
	[hospedaje] [varchar](100) NULL,
	[estado] [bit] NULL,
	[novedades] [varchar](100) NULL,
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

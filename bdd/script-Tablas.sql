SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[emergencia]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[emergencia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NULL,
	[estado] [bit] NULL,
 CONSTRAINT [PK_emergencia] PRIMARY KEY CLUSTERED 
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
	[EMPLCDGO] [nvarchar](255) NULL,
	[PRSNNMBR] [nvarchar](255) NULL,
	[PRSNAPLL] [nvarchar](255) NULL,
	[EMPLEMPR] [float] NULL,
	[PRSNNMCM] [nvarchar](255) NULL,
	[PRSNDRDM] [nvarchar](255) NULL,
	[PRSNTLFN] [nvarchar](255) NULL,
	[PRSNACAT] [nvarchar](255) NULL,
	[EMPLARDC] [nvarchar](255) NULL,
	[PRSNCRGO] [nvarchar](255) NULL,
	[PRSNMVIL] [nvarchar](255) NULL,
	[EMPLETDO] [nvarchar](255) NULL,
	[EMPLFAPR] [nvarchar](50) NULL,
	[PRSNCDLA] [nvarchar](255) NULL,
	[PRSNMAIL] [nvarchar](255) NULL,
	[PRSNNMCN] [nvarchar](255) NULL,
	[PRSNFFCN] [nvarchar](255) NULL,
	[STIOCDGO] [nvarchar](255) NULL,
	[STIODSCR] [nvarchar](255) NULL,
	[DPRTCNCS] [nvarchar](255) NULL,
	[DPRTDSCR] [nvarchar](255) NULL,
	[clave] [nvarchar](50) NULL,
	[estado] [bit] NULL,
	[rol] [nvarchar](50) NULL,
	[f_disponible] [datetime] NULL,
	[f_disponible2] [datetime] NULL,
	[observacion] [varchar](200) NULL,
	[observacion2] [varchar](200) NULL,
	[es_interno] [bit] NULL CONSTRAINT [DF_usuario_es_interno]  DEFAULT ((1)),
 CONSTRAINT [PK_TablaCompleta] PRIMARY KEY CLUSTERED 
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
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[solicitud]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[solicitud](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[f_creacion] [datetime] NULL,
	[direccion_origen] [varchar](150) NULL,
	[direccion_destino] [varchar](150) NULL,
	[f_salida] [datetime] NULL,
	[f_llegada] [datetime] NULL,
	[hospedaje] [varchar](100) NULL,
	[estado] [bit] NULL CONSTRAINT [DF_solicitud_estado]  DEFAULT ((0)),
	[cancelado] [bit] NULL CONSTRAINT [DF_solicitud_cancelado]  DEFAULT ((0)),
	[estado_enfermeria] [bit] NULL,
	[novedades] [varchar](100) NULL,
	[emergencia] [bit] NULL CONSTRAINT [DF_solicitud_emergencia]  DEFAULT ((0)),
	[ids_interno] [varchar](500) NULL,
	[ids_externo] [varchar](500) NULL,
	[id_solicitud_relacion] [int] NULL,
	[id_distancia] [int] NULL,
	[id_usuario_solicita] [int] NULL,
	[id_usuario_conductor] [int] NULL,
	[id_usuario_conductor2] [int] NULL,
	[id_usuario_aprobador] [int] NULL,
	[id_usuario_enfermero] [int] NULL,
	[id_tipo_emergencia] [int] NULL,
 CONSTRAINT [PK_solicitud] PRIMARY KEY CLUSTERED 
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
	[distancia] [float] NULL CONSTRAINT [DF_distancia_distancia]  DEFAULT ((0.0)),
	[id_origen] [int] NULL,
	[id_destino] [int] NULL,
 CONSTRAINT [PK_distancia] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_distancia]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_distancia] FOREIGN KEY([id_distancia])
REFERENCES [dbo].[distancia] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_distancia]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_emergencia]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_emergencia] FOREIGN KEY([id_tipo_emergencia])
REFERENCES [dbo].[emergencia] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_emergencia]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_usuario] FOREIGN KEY([id_usuario_aprobador])
REFERENCES [dbo].[usuario] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_usuario]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_usuario1]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_usuario1] FOREIGN KEY([id_usuario_enfermero])
REFERENCES [dbo].[usuario] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_usuario1]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_usuario3]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_usuario3] FOREIGN KEY([id_usuario_solicita])
REFERENCES [dbo].[usuario] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_usuario3]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_solicitud_usuario4]') AND parent_object_id = OBJECT_ID(N'[dbo].[solicitud]'))
ALTER TABLE [dbo].[solicitud]  WITH CHECK ADD  CONSTRAINT [FK_solicitud_usuario4] FOREIGN KEY([id_usuario_conductor])
REFERENCES [dbo].[usuario] ([id])
GO
ALTER TABLE [dbo].[solicitud] CHECK CONSTRAINT [FK_solicitud_usuario4]
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

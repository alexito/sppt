USE [sistemappt]
GO
/****** Object:  Table [dbo].[Usuarios] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Usuarios](
	[Id_Usuarios] [int] IDENTITY(1,1) NOT NULL,
	[Nombres] [varchar](30) COLLATE Modern_Spanish_CI_AS NULL,
	[Apellidos] [varchar](30) COLLATE Modern_Spanish_CI_AS NULL,
	[Rol] [varchar](20) COLLATE Modern_Spanish_CI_AS NULL,
	[Cedula] [varchar](10) COLLATE Modern_Spanish_CI_AS NULL,
	[Password] [varchar](30) COLLATE Modern_Spanish_CI_AS NULL,
	[Estado] [bit] NULL,
	[Email] [varchar](50) COLLATE Modern_Spanish_CI_AS NULL,
	[Telefono] [varchar](13) COLLATE Modern_Spanish_CI_AS NULL,
PRIMARY KEY CLUSTERED 
(
	[Id_Usuarios] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF


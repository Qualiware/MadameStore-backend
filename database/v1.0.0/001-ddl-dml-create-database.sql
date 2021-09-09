
--
-- TOC entry 203 (class 1259 OID 36759)
-- Name: tbl_funcionalidade_modulo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_funcionalidade_modulo (
    id_funcionalidade_modulo bigint NOT NULL,
    codg_mnemonico_funcionalidade character varying(40) NOT NULL,
    nome_funcionalidade_modulo character varying(200) NOT NULL,
    stat_funcionalidade_modulo character varying(1) NOT NULL,
    id_modulo_sistema bigint NOT NULL
);


ALTER TABLE tbl_funcionalidade_modulo OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 36764)
-- Name: tbl_grupo_funcionalidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_grupo_funcionalidade (
    id_grupo_funcionalidade bigint NOT NULL,
    id_funcionalidade_modulo bigint NOT NULL,
    id_grupo_usuario bigint NOT NULL
);


ALTER TABLE tbl_grupo_funcionalidade OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 36769)
-- Name: tbl_grupo_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_grupo_usuario (
    id_grupo_usuario bigint NOT NULL,
    flag_grupo_admin_usuario character varying(1) NOT NULL,
    desc_grupo_usuario character varying(200),
    nome_grupo_usuario character varying(50),
    stat_grupo_usuario character varying(1) NOT NULL
);


ALTER TABLE tbl_grupo_usuario OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 36774)
-- Name: tbl_modulo_sistema; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_modulo_sistema (
    id_modulo_sistema bigint NOT NULL,
    codg_mnemonico_modulo character varying(40) NOT NULL,
    nome_modulo_sistema character varying(200) NOT NULL,
    stat_modulo_sistema character varying(1) NOT NULL
);


ALTER TABLE tbl_modulo_sistema OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 36745)
-- Name: tbl_s_func_modulo; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_s_func_modulo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_s_func_modulo OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 36747)
-- Name: tbl_s_grupo_funcionalidade; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_s_grupo_funcionalidade
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_s_grupo_funcionalidade OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 36749)
-- Name: tbl_s_grupo_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_s_grupo_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_s_grupo_usuario OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 36751)
-- Name: tbl_s_modulo_sistema; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_s_modulo_sistema
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_s_modulo_sistema OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 36753)
-- Name: tbl_s_telefone_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_s_telefone_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_s_telefone_usuario OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 36755)
-- Name: tbl_s_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_s_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_s_usuario OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 36757)
-- Name: tbl_s_usuario_grupo; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tbl_s_usuario_grupo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tbl_s_usuario_grupo OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 36779)
-- Name: tbl_telefone_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_telefone_usuario (
    codg_telefone_usuario bigint NOT NULL,
    ddd_telefone_usuario bigint,
    numr_telefone_usuario character varying(11) NOT NULL,
    tipo_telefone_usuario bigint NOT NULL,
    codg_usuario bigint NOT NULL
);


ALTER TABLE tbl_telefone_usuario OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 36784)
-- Name: tbl_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_usuario (
    codg_usuario bigint NOT NULL,
    stat_bloqueio_acesso character varying(1),
    stat_expirado_acesso character varying(1),
    numr_cpf character varying(14) NOT NULL,
    data_expirado_acesso timestamp without time zone,
    data_atualizado timestamp without time zone NOT NULL,
    data_cadastrado timestamp without time zone NOT NULL,
    email character varying(75) NOT NULL,
    login_usuario character varying(20) NOT NULL,
    nome_usuario character varying(65) NOT NULL,
    qtde_acesso numeric(2,0),
    cont_tentativa_acesso character varying(1),
    senha_usuario character varying(255) NOT NULL,
    stat_usuario character varying(1) NOT NULL,
    data_ultimo_acesso timestamp without time zone
);


ALTER TABLE tbl_usuario OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 36789)
-- Name: tbl_usuario_grupo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tbl_usuario_grupo (
    id_usuario_grupo bigint NOT NULL,
    id_grupo_usuario bigint NOT NULL,
    codg_usuario bigint NOT NULL
);


ALTER TABLE tbl_usuario_grupo OWNER TO postgres;

--
-- TOC entry 2854 (class 0 OID 36759)
-- Dependencies: 203
-- Data for Name: tbl_funcionalidade_modulo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tbl_funcionalidade_modulo VALUES (1, 'INCLUIR', 'Incluir', 'A', 1);
INSERT INTO tbl_funcionalidade_modulo VALUES (2, 'PESQUISAR', 'Pesquisar', 'A', 1);
INSERT INTO tbl_funcionalidade_modulo VALUES (3, 'ATIVAR_INATIVAR', 'Ativar/Inativar', 'A', 1);
INSERT INTO tbl_funcionalidade_modulo VALUES (4, 'ALTERAR', 'Alterar', 'A', 1);
INSERT INTO tbl_funcionalidade_modulo VALUES (5, 'VISUALIZAR', 'Visualizar', 'A', 1);
INSERT INTO tbl_funcionalidade_modulo VALUES (6, 'INCLUIR', 'Incluir', 'A', 2);
INSERT INTO tbl_funcionalidade_modulo VALUES (7, 'PESQUISAR', 'Pesquisar', 'A', 2);
INSERT INTO tbl_funcionalidade_modulo VALUES (8, 'ATIVAR_INATIVAR', 'Ativar/Inativar', 'A', 2);
INSERT INTO tbl_funcionalidade_modulo VALUES (9, 'ALTERAR', 'Alterar', 'A', 2);
INSERT INTO tbl_funcionalidade_modulo VALUES (10, 'VISUALIZAR', 'Visualizar', 'A', 2);


--
-- TOC entry 2855 (class 0 OID 36764)
-- Dependencies: 204
-- Data for Name: tbl_grupo_funcionalidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tbl_grupo_funcionalidade VALUES (1, 9, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (2, 4, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (3, 6, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (4, 7, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (5, 2, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (6, 3, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (7, 5, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (8, 1, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (9, 8, 1);
INSERT INTO tbl_grupo_funcionalidade VALUES (10, 10, 1);


--
-- TOC entry 2856 (class 0 OID 36769)
-- Dependencies: 205
-- Data for Name: tbl_grupo_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tbl_grupo_usuario VALUES (1, 'S', 'Grupo de Administradores', 'Administradores', 'A');


--
-- TOC entry 2857 (class 0 OID 36774)
-- Dependencies: 206
-- Data for Name: tbl_modulo_sistema; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tbl_modulo_sistema VALUES (1, 'USUARIO', 'Manter Usuário', 'A');
INSERT INTO tbl_modulo_sistema VALUES (2, 'GRUPO', 'Manter Grupo', 'A');


--
-- TOC entry 2858 (class 0 OID 36779)
-- Dependencies: 207
-- Data for Name: tbl_telefone_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2859 (class 0 OID 36784)
-- Dependencies: 208
-- Data for Name: tbl_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tbl_usuario VALUES (1, NULL, NULL, '10010010017', NULL, '2021-08-24 00:00:00', '2021-08-24 00:00:00', 'admin@teste.com.br', 'admin', 'Administrador', NULL, NULL, '$2a$10$g7pCvnGtsUTkZb206aC0x.TznrCayIrn.UYECH6setLtvYSvuResO', 'A', NULL);


--
-- TOC entry 2860 (class 0 OID 36789)
-- Dependencies: 209
-- Data for Name: tbl_usuario_grupo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tbl_usuario_grupo VALUES (1, 1, 1);


--
-- TOC entry 2866 (class 0 OID 0)
-- Dependencies: 196
-- Name: tbl_s_func_modulo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_s_func_modulo', 10, true);


--
-- TOC entry 2867 (class 0 OID 0)
-- Dependencies: 197
-- Name: tbl_s_grupo_funcionalidade; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_s_grupo_funcionalidade', 10, true);


--
-- TOC entry 2868 (class 0 OID 0)
-- Dependencies: 198
-- Name: tbl_s_grupo_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_s_grupo_usuario', 1, true);


--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 199
-- Name: tbl_s_modulo_sistema; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_s_modulo_sistema', 2, true);


--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 200
-- Name: tbl_s_telefone_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_s_telefone_usuario', 1, false);


--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 201
-- Name: tbl_s_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_s_usuario', 1, true);


--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 202
-- Name: tbl_s_usuario_grupo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tbl_s_usuario_grupo', 1, true);


--
-- TOC entry 2707 (class 2606 OID 36763)
-- Name: tbl_funcionalidade_modulo tbl_funcionalidade_modulo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_funcionalidade_modulo
    ADD CONSTRAINT tbl_funcionalidade_modulo_pkey PRIMARY KEY (id_funcionalidade_modulo);


--
-- TOC entry 2709 (class 2606 OID 36768)
-- Name: tbl_grupo_funcionalidade tbl_grupo_funcionalidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_grupo_funcionalidade
    ADD CONSTRAINT tbl_grupo_funcionalidade_pkey PRIMARY KEY (id_grupo_funcionalidade);


--
-- TOC entry 2711 (class 2606 OID 36773)
-- Name: tbl_grupo_usuario tbl_grupo_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_grupo_usuario
    ADD CONSTRAINT tbl_grupo_usuario_pkey PRIMARY KEY (id_grupo_usuario);


--
-- TOC entry 2713 (class 2606 OID 36778)
-- Name: tbl_modulo_sistema tbl_modulo_sistema_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_modulo_sistema
    ADD CONSTRAINT tbl_modulo_sistema_pkey PRIMARY KEY (id_modulo_sistema);


--
-- TOC entry 2715 (class 2606 OID 36783)
-- Name: tbl_telefone_usuario tbl_telefone_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_telefone_usuario
    ADD CONSTRAINT tbl_telefone_usuario_pkey PRIMARY KEY (codg_telefone_usuario);


--
-- TOC entry 2719 (class 2606 OID 36793)
-- Name: tbl_usuario_grupo tbl_usuario_grupo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_usuario_grupo
    ADD CONSTRAINT tbl_usuario_grupo_pkey PRIMARY KEY (id_usuario_grupo);


--
-- TOC entry 2717 (class 2606 OID 36788)
-- Name: tbl_usuario tbl_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_usuario
    ADD CONSTRAINT tbl_usuario_pkey PRIMARY KEY (codg_usuario);


--
-- TOC entry 2720 (class 2606 OID 36794)
-- Name: tbl_funcionalidade_modulo fk69wh08in5rvomym8m8e2orjp8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_funcionalidade_modulo
    ADD CONSTRAINT fk69wh08in5rvomym8m8e2orjp8 FOREIGN KEY (id_modulo_sistema) REFERENCES tbl_modulo_sistema(id_modulo_sistema);


--
-- TOC entry 2724 (class 2606 OID 36814)
-- Name: tbl_usuario_grupo fk7u893i5afy0xy7s6923n94so0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_usuario_grupo
    ADD CONSTRAINT fk7u893i5afy0xy7s6923n94so0 FOREIGN KEY (id_grupo_usuario) REFERENCES tbl_grupo_usuario(id_grupo_usuario);


--
-- TOC entry 2721 (class 2606 OID 36799)
-- Name: tbl_grupo_funcionalidade fk9qffbqv2umgwjv69r9ki29yas; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_grupo_funcionalidade
    ADD CONSTRAINT fk9qffbqv2umgwjv69r9ki29yas FOREIGN KEY (id_funcionalidade_modulo) REFERENCES tbl_funcionalidade_modulo(id_funcionalidade_modulo);


--
-- TOC entry 2723 (class 2606 OID 36809)
-- Name: tbl_telefone_usuario fkf77khq6u245al7569b9ggh5nl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_telefone_usuario
    ADD CONSTRAINT fkf77khq6u245al7569b9ggh5nl FOREIGN KEY (codg_usuario) REFERENCES tbl_usuario(codg_usuario);


--
-- TOC entry 2722 (class 2606 OID 36804)
-- Name: tbl_grupo_funcionalidade fki5rge57hfbk849l3o19h0hs; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_grupo_funcionalidade
    ADD CONSTRAINT fki5rge57hfbk849l3o19h0hs FOREIGN KEY (id_grupo_usuario) REFERENCES tbl_grupo_usuario(id_grupo_usuario);


--
-- TOC entry 2725 (class 2606 OID 36819)
-- Name: tbl_usuario_grupo fkse8ga8xolrna8owasauqfuegj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tbl_usuario_grupo
    ADD CONSTRAINT fkse8ga8xolrna8owasauqfuegj FOREIGN KEY (codg_usuario) REFERENCES tbl_usuario(codg_usuario);


-- Completed on 2021-08-24 00:38:28

--
-- PostgreSQL database dump complete
--


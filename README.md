# Teradata VantageでURLエンコードするための関数作成プロジェクト
このプロジェクトは、Teradata UDF でURLエンコードとデコードを行うため関数作成のためのプロジェクトです。  
この関数は次の機能があります。
- String URLEnc(String,String)
　指定された文字列をエンコード文字列に置き換えます。<br>
　第2引数はエンコードタイプとなる、UTF-8,UTF-16,Windows-31Jを指定します。
- String URLDec(String,String)
　指定されたエンコード文字列を文字列に置き換えます。<br>
　第2引数はエンコードタイプとなる、UTF-8,UTF-16,Windows-31Jを指定します。

## (1)Jarfileを作成する
Javaソースプログラムをコンパイルして任意のフォルダにjarファイルをエクスポートします。  
こちらの例では、"C:\temp"の下にjarファイルを出力しています。  
最新のコンパイル済みJarファイルは、**本プロジェクト内のDistフォルダ**にあります。

	C:/temp/URLEncDec.jar

## (2)Teradataのdbcにログインします

.logon dbc,{パスワード}

## (3)UDF権限を付与
GRANT EXECUTE PROCEDURE ON sqlj to {ターゲットユーザ};
GRANT FUNCTION  ON {ターゲットユーザ} TO {ターゲットユーザ};

## (4)TeradataのUDFを実行するユーザにログインします

.logon {ターゲットユーザ},{パスワード}

## (5)JarfileをDBからリムーブする。(6)未実施の場合はスキップしてください。
CALL SQLJ.REMOVE_JAR('URLEncDec', 0); 

## (6)JarfileをDBにインポートする

CALL SQLJ.INSTALL_JAR('CJ!C:/temp/URLEncDec.jar', 'URLEncDec', 0); 

## (7)UDF関数を定義する

	replace FUNCTION URLEnc(p1 varchar(1024) Character set unicode ,p2 varchar(64) Character set unicode)
	RETURNS varchar(1024) character set unicode
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'URLEncDec:com.teradata.URLEncDec.enc(java.lang.String,java.lang.String) returns java.lang.String';

	replace FUNCTION URLDec(p1 varchar(1024) character set unicode ,p2 varchar(64) Character set unicode)
	RETURNS varchar(1024) character set unicode
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'URLEncDec:com.teradata.URLEncDec.dec(java.lang.String,java.lang.String) returns java.lang.String';

## (8)UDF関数の実行方法。
### (8.1)URLエンコードします
	select URLEnc({文字列},{エンコードタイプ});

※第2引数はエンコードタイプとなる、UTF-8,UTF-16,Windows-31Jを指定します。


### (8.2)URLデコードします
	select URLDec({エンコード文字列},{エンコードタイプ});

※第2引数はエンコードタイプとなる、UTF-8,UTF-16,Windows-31Jを指定します。
	

package ar.edu.unlam.criptografia.cifrador;

public class Grain {
	
	private byte[] textoPlano;
	private byte[] keystream;
	private int longitud;
	private KeystreamGenerator keystreamGenerator;

	public Grain(byte[] clave, byte[] semilla, byte[] textoPlano) throws Exception {
		keystreamGenerator = new KeystreamGenerator(toShortArray(clave), toShortArray(semilla));

		this.textoPlano = textoPlano;
		this.keystream = toByteArray(keystreamGenerator.generarKeystream(this.textoPlano.length - 54));

		if ((this.longitud = this.textoPlano.length) - 54 != this.keystream.length) {
			throw new Exception();
		}
	}

	public byte[] xor() {
		byte[] xored = new byte[this.longitud];
		byte[] header = getHeader(this.textoPlano);
		byte[] body = getBody(this.textoPlano);

		for (int i = 0; i < 54; i++) {
			xored[i] = header[i];
		}
		
		for (int i = 0; i < this.longitud - 54; i++) {
			xored[i + 54] = (byte) ((body[i]) ^ this.keystream[i]);
		}
		
		return xored;
	}

	private byte[] toByteArray(short[] virgin) {
		byte[] chad = new byte[virgin.length / 8];
		for (int i = 0; i < (virgin.length / 8); i++) {
			chad[i] = (byte) (virgin[i * 8] * 128 + virgin[i * 8 + 1] * 64 + virgin[i * 8 + 2] * 32 + virgin[i * 8 + 3] * 16
					+ virgin[i * 8 + 4] * 8 + virgin[i * 8 + 5] * 4 + virgin[i * 8 + 6] * 2 + virgin[i * 8 + 7]);
		}
		return chad;
	}

	private short[] toShortArray(byte[] chad) {
		short[] virgin = new short[chad.length * 8];
		for (int i = 0; i < chad.length; i++) {
			byte aux = chad[i];
			for (int j = 7; j >= 0; j--) {
				virgin[i * 8 + j] = (short) (aux % 2);
				aux = (byte) (aux / 2);
			}
		}
		return virgin;
	}

	private byte[] getHeader(byte[] textoPlano) {
		byte[] header = new byte[54];
		for (int i = 0; i < 54; i++) {
			header[i] = textoPlano[i];
		}	
		return header;
	}

	private byte[] getBody(byte[] textoPlano) {
		int longitud = textoPlano.length - 54;
		byte[] body = new byte[longitud];
		for (int i = 0; i < longitud; i++) {
			body[i] = textoPlano[i + 54];
		}
		return body;
	}
}

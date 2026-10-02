import http.server
import os

class RangeRequestHandler(http.server.SimpleHTTPRequestHandler):
    def send_head(self):
        path = self.translate_path(self.path)
        if os.path.isdir(path):
            return super().send_head()
            
        range_header = self.headers.get('Range')
        if not range_header or not range_header.startswith('bytes='):
            return super().send_head()

        try:
            size = os.path.getsize(path)
            byte_range = range_header.split('=')[1].split('-')
            start = int(byte_range[0])
            end = int(byte_range[1]) if byte_range[1] else size - 1

            if start >= size:
                self.send_error(416, 'Requested Range Not Satisfiable')
                return None

            f = open(path, 'rb')
            f.seek(start)

            self.send_response(206)
            self.send_header('Content-type', self.guess_type(path))
            self.send_header('Accept-Ranges', 'bytes')
            self.send_header('Content-Range', f'bytes {start}-{end}/{size}')
            self.send_header('Content-Length', str(end - start + 1))
            self.end_headers()
            return f
        except Exception:
            return super().send_head()

if __name__ == '__main__':
    http.server.test(HandlerClass=RangeRequestHandler, port=8000)
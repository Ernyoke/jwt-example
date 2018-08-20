import { MyResponse } from './model/Response';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OtherService {

  constructor(private http: HttpClient) { }

  requestAdminResource(): Observable<MyResponse> {
    return this.http.get<MyResponse>('/api/admin');
  }

  requestUserResource(): Observable<MyResponse> {
    return this.http.get<MyResponse>('/api/user');
  }
}

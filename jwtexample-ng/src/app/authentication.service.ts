import { Token } from './model/Token';
import { Credentials } from './model/Credentials';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { tap, catchError, map } from 'rxjs/operators';

import * as moment from 'moment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private EXPIRES_AT = 'expiresAt';
  private TOKEN = 'token';

  constructor(private http: HttpClient) { }

  isAuthenticated(): Observable<boolean> {
    try {
      if (moment().isBefore(this.getExpiration())) {
        return of(true);
      } else {
        return of(false);
      }
    } catch (ex) {
      return of(false);
    }
  }

  getToken() {
    try {
      return localStorage.getItem(this.TOKEN);
    } catch (exception) {
      return null;
    }
  }

  getExpiration() {
    const expiration = localStorage.getItem(this.EXPIRES_AT);
    if (!expiration) {
      throw new Error('Token does not exist!');
    }
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }

  login(credentials: Credentials) {
    return this.http.post('/api/login', credentials).pipe(
      tap((response: Token) => {
        this.setSession(response);
      }),
      catchError((error: HttpErrorResponse) => {
        return throwError(`Invalid login: ${error.statusText}`);
      })
    );
  }

  // Clear the localStorage in case of logout.
  logout() {
    localStorage.removeItem(this.TOKEN);
    localStorage.removeItem(this.EXPIRES_AT);
  }

    // Store the JWT token and its expiration date inside local storage.
    private setSession(token: Token) {
      const expiresAt = moment().add(token.expiresIn, 'second');
      localStorage.setItem(this.TOKEN, token.token);
      localStorage.setItem(this.EXPIRES_AT, JSON.stringify(expiresAt.valueOf()));
    }
}

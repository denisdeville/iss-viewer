import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IssCoordinates } from '../models/iss-coordinates';

@Injectable({
  providedIn: 'root'
})
export class SatellitePositionService {

  private baseUrl = `${environment.apiUrl}`;

  constructor(private httpClient: HttpClient) { }

  public getSatelliteCurrentPosition(): Observable<IssCoordinates> {
    return this.httpClient.get<IssCoordinates>(`${this.baseUrl}/iss/position`)
  }
}

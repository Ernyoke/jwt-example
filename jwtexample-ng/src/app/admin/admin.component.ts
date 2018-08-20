import { HttpErrorResponse } from '@angular/common/http';
import { MyResponse } from './../model/Response';
import { ErrorResponse } from './../model/ErrorResponse';
import { Component, OnInit } from '@angular/core';
import { OtherService } from '../other.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  public response: MyResponse;
  public errorResponse: String;
  public error: boolean;

  constructor(private otherService: OtherService) {
    this.response = new MyResponse();
    this.error = false;
  }

  ngOnInit() {
    this.otherService.requestAdminResource().subscribe((r: MyResponse) => {
      this.response = r;
    }, (httpError: HttpErrorResponse) => {
      this.error = true;
      this.errorResponse = httpError.message;
    });
  }

}
